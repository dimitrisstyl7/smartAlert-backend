package com.unipi.smartalert.models;

import com.unipi.smartalert.enums.GroupStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "report_groups")
public class ReportGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "central_point", nullable = false)
    private Point centralPoint;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GroupStatus status;

    @Column(name = "search_radius_in_meters", nullable = false)
    private double searchRadiusInMeters;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private IncidentCategory category;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private List<IncidentReport> reports;

}