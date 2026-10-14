package com.greenabocado.schedulemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SchedulemanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulemanagerApplication.class, args);
    }

}
