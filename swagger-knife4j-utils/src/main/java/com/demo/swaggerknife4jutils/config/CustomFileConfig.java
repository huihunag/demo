package com.demo.swaggerknife4jutils.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "custom.config.file")
public class CustomFileConfig {

    private String upDownloadAddr;

    public String getUpDownloadAddr() {
        return upDownloadAddr;
    }
}
