package com.super30.springbasics;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class TheBestStudentHealthIndicator implements HealthIndicator {

    private final TheBestStudentRepository repo;

    TheBestStudentHealthIndicator(TheBestStudentRepository repo) {
        this.repo = repo;
    }

    @Override
    public Health health() {
        try {
            long count = repo.count();
            return Health.up()
                    .withDetail("studentCount", count)
                    .build();
        } catch (Exception e) {
            return Health.down(e).build();
        }
    }
}
