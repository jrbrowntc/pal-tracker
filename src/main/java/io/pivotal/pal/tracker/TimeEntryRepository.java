package io.pivotal.pal.tracker;

import io.pivotal.pal.trackerapi.TimeEntry;

import java.util.List;

public interface TimeEntryRepository {
    TimeEntry create(TimeEntry timeEntry);

    TimeEntry find(long timeEntryId);

    List<TimeEntry> list();

    TimeEntry update(long id, TimeEntry timeEntry);

    void delete(long timeEntryId);
}
