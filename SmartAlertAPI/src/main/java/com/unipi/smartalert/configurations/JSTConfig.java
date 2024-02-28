package com.unipi.smartalert.configurations;

import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JSTConfig {

    @Bean
    public GeometryFactory geometryFactory() {
        return new GeometryFactory();
    }

}
