package com.unipi.smartalert.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "incident_category_name")
public class IncidentCategoryName {

    @Id
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Id
    @Column(name = "language", nullable = false, length = 2)
    private String language;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

}
