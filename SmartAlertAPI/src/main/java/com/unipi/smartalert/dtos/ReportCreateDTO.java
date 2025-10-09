// src/main/java/.../dtos/ReportCreateDTO.java
package com.unipi.smartalert.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportCreateDTO {
    private Long categoryId;
    private LocationDTO location;
    private String timestamp;
    private String description;
    private boolean hasImage;
}
