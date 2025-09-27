package com.unipi.smartalert.controllers;

import com.unipi.smartalert.services.StatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("api/v1/statistics")
@AllArgsConstructor
public class StatisticsController {

    private StatisticsService statisticsService;

    @GetMapping
    public Map<String, Double> getStatistics(@RequestParam int month, @RequestParam int year) {
        Locale locale = LocaleContextHolder.getLocale();
        return statisticsService.getStatistics(month, year, locale.getLanguage());
    }

}
