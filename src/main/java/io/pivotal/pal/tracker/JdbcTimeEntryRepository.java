package io.pivotal.pal.tracker;

import io.pivotal.pal.trackerapi.TimeEntry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;

    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry any) {

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "insert into time_entries (project_id, user_id, date, hours) VALUES (?,?,?,?)", RETURN_GENERATED_KEYS
            );
            statement.setLong(1, any.getProjectId());
            statement.setLong(2, any.getUserId());
            statement.setDate(3, Date.valueOf(any.getDate()));
            statement.setLong(4, any.getHours());

            return statement;
        }, generatedKeyHolder);
        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        return jdbcTemplate.query("Select id, project_id, user_id, date, hours from time_entries where id = ?",
                new Object[]{timeEntryId}, extractor);
    }

    @Override
    public List<TimeEntry> list() {
        return jdbcTemplate.query("Select id, project_id, user_id, date, hours from time_entries", mapper);
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        jdbcTemplate.update("UPDATE time_entries SET project_id=?, user_id=?, date=?, hours=? WHERE id = ?",
            timeEntry.getProjectId(),
            timeEntry.getUserId(),
            Date.valueOf(timeEntry.getDate()),
            timeEntry.getHours(),
            id);
        return find(id);
    }

    @Override
    public void delete(long timeEntryId) {
        jdbcTemplate.update("DELETE FROM time_entries WHERE id="+timeEntryId);
    }
}
