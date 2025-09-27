package com.unipi.smartalert.mappers;

import com.unipi.smartalert.dtos.LocationDTO;
import com.unipi.smartalert.dtos.ReportDTO;
import com.unipi.smartalert.models.IncidentReport;
import com.unipi.smartalert.repositories.UserRepository;
import com.unipi.smartalert.services.IncidentCategoryService;
import com.unipi.smartalert.utils.SecurityUtil;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@AllArgsConstructor
public class IncidentReportMapper {

    private final IncidentCategoryService incidentCategoryService;
    private final GeometryFactory geometryFactory;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public IncidentReport mapToIncidentReport(ReportDTO reportDTO) {
        Point point = geometryFactory.createPoint(
                new Coordinate(
                        reportDTO.getLocation().getLongitude(),
                        reportDTO.getLocation().getLatitude())
        );
        point.setSRID(4326);

        return IncidentReport.builder()
                .user(userRepository.findByEmail(SecurityUtil.getAuthenticatedUserEmail()))
                .category(incidentCategoryService.findById(reportDTO.getCategoryId()))
                .location(point)
                .description(reportDTO.getDescription())
                .createdAt(Timestamp.valueOf(reportDTO.getTimestamp()))
                .build();
    }

    public ReportDTO mapToDTO(IncidentReport incidentReport) {
        boolean hasImage = (incidentReport.getImagePath() != null && !incidentReport.getImagePath().isEmpty());
        return ReportDTO.builder()
                .id(incidentReport.getId())
                .categoryId(incidentReport.getId())
                .location(new LocationDTO(incidentReport.getLocation().getY(), incidentReport.getLocation().getX()))
                .timestamp(incidentReport.getCreatedAt().toString())
                .description(incidentReport.getDescription())
                .sender(userMapper.apply(incidentReport.getUser()))
                .hasImage(hasImage)
                .build();
    }

}
