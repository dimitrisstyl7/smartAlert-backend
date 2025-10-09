package com.unipi.smartalert.controllers;

import com.unipi.smartalert.dtos.ReportCreateDTO;
import com.unipi.smartalert.dtos.ReportDTO;
import com.unipi.smartalert.dtos.UserDTO;
import com.unipi.smartalert.services.IncidentReportService;
import com.unipi.smartalert.services.UserService;
import com.unipi.smartalert.services.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/reports")
public class IncidentReportController {

    private final IncidentReportService service;
    private final UserServiceImpl userServiceImpl;

    // GET /reports/{id} - Get the image of an incident report by ID
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImageByReportId(@PathVariable int id) {
        byte[] imgData = service.getImage(id);

        // Set content type header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imgData, headers, HttpStatus.OK);
    }

    // POST /reports - Create a new incident report
    @PostMapping
    public void create(
            @RequestPart(name = "report") ReportCreateDTO reportCreate,
            @RequestPart(name = "image", required = false) MultipartFile image,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        UserDTO sender = userServiceImpl.getUser(userDetails.getUsername());

        ReportDTO fullReport = ReportDTO.builder()
                .categoryId(reportCreate.getCategoryId())
                .location(reportCreate.getLocation())
                .timestamp(reportCreate.getTimestamp())
                .description(reportCreate.getDescription())
                .hasImage(reportCreate.isHasImage())
                .sender(sender)
                .build();

        service.saveReport(fullReport, image);
    }
}


