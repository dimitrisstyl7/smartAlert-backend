package com.unipi.smartalert.controllers;

import com.unipi.smartalert.dtos.ReportDTO;
import com.unipi.smartalert.dtos.ReportGroupDTO;
import com.unipi.smartalert.enums.GroupStatus;
import com.unipi.smartalert.services.ReportGroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/groups")
public class ReportGroupController {

    private final ReportGroupService service;

    @GetMapping
    public List<ReportGroupDTO> getAll() {
        return service.findAllAcceptedGroupsWithin24Hours();
    }

    @GetMapping("/{id}/reports")
    public List<ReportDTO> getReportsByGroupId(@PathVariable("id") long id) {
        return service.getReportsByGroupId(id);
    }

    @PatchMapping("/{id}/accept")
    public ResponseEntity<Void> acceptGroup(@PathVariable long id) {
        // Change the group status to ACCEPTED
        service.changeStatus(id, GroupStatus.ACCEPTED);
        // Return 204 if no exception was thrown
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/decline")
    public ResponseEntity<Void> declineGroup(@PathVariable long id) {
        // Change the group status to DECLINED
        service.changeStatus(id, GroupStatus.DECLINED);
        // Return 204 if no exception was thrown
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
