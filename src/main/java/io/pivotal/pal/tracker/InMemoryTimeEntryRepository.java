package io.pivotal.pal.tracker;

import io.pivotal.pal.trackerapi.TimeEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private Long currentId = 1L;
    private HashMap<Long, TimeEntry> timeEntryHashMap = new HashMap<>();

    @Override
    public TimeEntry create(TimeEntry any) {
        any.setId(currentId);
        timeEntryHashMap.put(currentId,any);
        currentId += 1L;
        return any;
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        return timeEntryHashMap.get(timeEntryId);
    }

    @Override
    public List<TimeEntry> list()
    {
        return new ArrayList<>(timeEntryHashMap.values());
    }

    @Override
    public TimeEntry update(long id, TimeEntry any) {
        any.setId(id);
        timeEntryHashMap.remove(id);
        timeEntryHashMap.put(id, any);
        return any;
    }

    @Override
    public void delete(long timeEntryId) {
        timeEntryHashMap.remove(timeEntryId);
    }
}
