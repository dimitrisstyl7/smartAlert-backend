package com.unipi.smartalert.controllers;

import com.unipi.smartalert.dtos.IncidentCategoryDTO;
import com.unipi.smartalert.services.IncidentCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/incident-categories")
public class IncidentCategoryController {

    private final IncidentCategoryService service;

    // GET /incidents - Get all incidents
    @GetMapping
    public List<IncidentCategoryDTO> getAll() {
        Locale locale = LocaleContextHolder.getLocale();
        return service.findByLanguage(locale.getLanguage());
    }

}
