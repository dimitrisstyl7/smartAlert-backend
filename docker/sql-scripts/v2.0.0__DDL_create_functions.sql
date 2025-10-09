-- Find or create a report group based on proximity
CREATE OR REPLACE FUNCTION assign_report_to_group()
    RETURNS TRIGGER AS
$$
DECLARE
    nearest_group_id INTEGER;
BEGIN

    -- Check if the report is close to an existing group
    SELECT id
    INTO nearest_group_id
    FROM report_groups
    -- group's category is the same as report's
    WHERE report_groups.category_id = NEW.category_id
      -- report timestamp is within 2 hours from group last_updated
      AND ABS(EXTRACT(epoch FROM report_groups.last_updated - NEW.created_at) / 3600) <= 2
      -- report location and group central point are within group search radius
      AND ST_DWithin(NEW.location::geography, central_point::geography, search_radius_in_meters)
    ORDER BY ST_Distance(NEW.location, central_point)
    LIMIT 1;

    RAISE NOTICE 'Close to group with id: %', nearest_group_id;

    -- If no existing group found, create a new group
    IF nearest_group_id IS NULL THEN

        INSERT INTO report_groups (category_id, central_point, search_radius_in_meters, last_updated)
        VALUES (NEW.category_id,
                NEW.location,
                (SELECT init_search_radius_in_meters FROM incident_categories WHERE id = NEW.category_id),
                CURRENT_TIMESTAMP)
        RETURNING id INTO nearest_group_id;

        RAISE NOTICE 'Created new group with id: %', nearest_group_id;

    ELSE

        -- Update the central location and the search radius of the assigned group

        WITH group_locations AS (
            SELECT location AS report_location
            FROM incident_report
            WHERE group_id = nearest_group_id
            UNION ALL
            SELECT NEW.location AS report_location
        ),
             group_central_point AS (
                 SELECT ST_Centroid(ST_Collect(report_location)) AS group_central_point
                 FROM group_locations
             ),
             distances AS (
                 SELECT ST_Distance(group_central_point::geography, report_location::geography) AS distance
                 FROM group_locations, group_central_point
             ),
             avg_distance AS (
                 SELECT avg(distance) AS mean
                 FROM distances
             ),
             standard_deviation AS (
                 SELECT sqrt(sum(power(distance - mean, 2)) / COUNT(distances)) AS standard_deviation
                 FROM distances, avg_distance
             )

        UPDATE report_groups
        SET central_point           = (SELECT group_central_point FROM group_central_point),
            search_radius_in_meters = (SELECT init_search_radius_in_meters
                                       FROM incident_categories
                                       WHERE id = (SELECT category_id FROM report_groups WHERE id = nearest_group_id))
                + (2.5 * (SELECT standard_deviation FROM standard_deviation)),
            last_updated            = CURRENT_TIMESTAMP
        WHERE id = nearest_group_id;

        RAISE NOTICE 'Updated the group';

    END IF;

    -- Assign the report to the determined group
    NEW.group_id := nearest_group_id;

    RETURN NEW;
END
$$
    LANGUAGE plpgsql;

-- Create a trigger to execute the function before a new report gets inserted
CREATE OR REPLACE TRIGGER assign_report_trigger
    BEFORE INSERT
    ON incident_reports
    FOR EACH ROW
EXECUTE FUNCTION assign_report_to_group();
