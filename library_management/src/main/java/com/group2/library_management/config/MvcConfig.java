package com.group2.library_management.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // Inject value from application.properties into uploadDir variable
    @Value("${application.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map URL /uploads/**
        registry.addResourceHandler("/uploads/**")
                // To the physical directory. "file:" is required to specify this is a file system path.
                .addResourceLocations("file:" + uploadDir + "/");
    }
}
