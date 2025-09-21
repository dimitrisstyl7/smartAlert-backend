package com.unipi.smartalert.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@Data
@ConfigurationProperties(prefix = "gcp.firebase")
public class FirebaseProperties {

    // Gets the service account JSON file from the classpath.
    // The classpath definition is located in the application.yml file.
    private Resource serviceAccount;
    private String databaseUrl;
    
}
