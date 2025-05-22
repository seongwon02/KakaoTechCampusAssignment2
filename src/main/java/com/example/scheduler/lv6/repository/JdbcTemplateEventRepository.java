package com.example.scheduler.lv6.repository;

import com.example.scheduler.lv6.dto.EventWithUsernameResponseDto;
import com.example.scheduler.lv6.entitiy.Event;
import com.example.scheduler.lv6.exception.EventNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class JdbcTemplateEventRepository implements EventRepository {

    private final JdbcTemplate jdbcTemplate;


    public JdbcTemplateEventRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Event saveEvent(Event event) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("event")
                .usingGeneratedKeyColumns("id")
                .usingColumns("user_id", "password", "title", "contents");;

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", event.getUser_id());
        parameters.put("password", event.getPassword());
        parameters.put("title", event.getTitle());
        parameters.put("contents", event.getContents());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        // log.info("event_id : {}", key.longValue());
        return new Event(key.longValue(), event.getUser_id(), event.getPassword(), event.getTitle(), event.getContents());
    }

    @Override
    public List<EventWithUsernameResponseDto> findAllFilteredEvent(Long user_id, LocalDate modified_at, Long offset, Integer size) {
        String sql = "SELECT e.id, e.user_id, e.title, e.contents, u.name " +
                "FROM event e JOIN user u ON e.user_id = u.id";
        List<EventWithUsernameResponseDto> result;

        if (user_id != null && modified_at != null) {
            sql += " WHERE e.user_id = ? AND DATE(e.modified_at) = ? ORDER BY e.modified_at desc limit ? offset ?";
            result = jdbcTemplate.query(sql, EventRowMapper(), user_id, modified_at, size, offset);
        }
        else if (user_id != null) {
            sql += " WHERE e.user_id = ? ORDER BY e.modified_at desc limit ? offset ?";
            result = jdbcTemplate.query(sql, EventRowMapper(), user_id, size, offset);
        }
        else if (modified_at != null) {
            sql += " WHERE DATE(e.modified_at) = ? ORDER BY e.modified_at desc limit ? offset ?";
            result = jdbcTemplate.query(sql, EventRowMapper(),  modified_at, size, offset);
        }
        else {
            sql += " ORDER BY e.modified_at desc limit ? offset ?";
            result = jdbcTemplate.query(sql, EventRowMapper(), size, offset);
        }

        return result;
    }

    @Override
    public Event findEventByIdOrElseThrow(Long id) {
        String sql = "SELECT * FROM event WHERE id = ?";
        List<Event> result = jdbcTemplate.query(sql, EventRowMapperV2(), id);

        return result.stream().findAny().orElseThrow(EventNotFoundException::new);
    }

    @Override
    public int updateContents(Long id, String contents) {
        String sql = "UPDATE event SET";

        return jdbcTemplate.update(sql + " contents = ? WHERE id = ?", contents, id);
    }

    @Override
    public void deleteEvent(Long id) {
        jdbcTemplate.update("delete from event where id = ?", id);
    }

    @Override
    public long countEvents() throws NullPointerException{
        String sql = "SELECT COUNT(*) FROM event";

        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    private RowMapper<EventWithUsernameResponseDto> EventRowMapper() {
        return new RowMapper<EventWithUsernameResponseDto>() {
            @Override
            public EventWithUsernameResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new EventWithUsernameResponseDto(
                        rs.getLong("e.id"),
                        rs.getLong("e.user_id"),
                        rs.getString("u.name"),
                        rs.getString("e.title"),
                        rs.getString("e.contents")
                );
            }

        };
    }

    private RowMapper<Event> EventRowMapperV2() {
        return new RowMapper<Event>() {
            @Override
            public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Event(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("password"),
                        rs.getString("title"),
                        rs.getString("contents")
                );
            }

        };
    }
}
