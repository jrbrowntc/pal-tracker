package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class TimeEntryHealthIndicator implements HealthIndicator {
    private TimeEntryRepository timeEntryRepository;

    public TimeEntryHealthIndicator(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }
    @Override
    public Health health() {
        Health.Builder healthBuilder = new Health.Builder();
        if(timeEntryRepository.list().size() < 5) {
            healthBuilder.up();
        } else {
            healthBuilder.down();
        }
        return healthBuilder.build();
    }
}
