package com.skillscan;

import com.skillscan.authentication.SecurityConfigurations;
import com.skillscan.configuration.ServiceConfiguration;
import com.skillscan.configuration.UseCaseConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableCaching
@Import({UseCaseConfiguration.class, ServiceConfiguration.class, SecurityConfigurations.class})
public class SkillScanApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkillScanApplication.class, args);
    }
}
