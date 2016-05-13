package com.memorynotfound.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import com.memorynotfound.config.AppConfig;

@SpringBootApplication
@Import({AppConfig.class})
public class BeerApplication {

    private static Logger log = LoggerFactory.getLogger(BeerApplication.class);
    
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(BeerApplication.class);
        ApplicationContext context = springApplication.run(args);
        log.info("Context : " + context.getId());
    }

}
