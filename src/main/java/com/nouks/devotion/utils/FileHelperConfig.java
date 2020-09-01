package com.nouks.devotion.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "resources")
@Data
public class FileHelperConfig {
    private String rootFolder;
    private String logoFolder;
    private String widgetFolder;
}
