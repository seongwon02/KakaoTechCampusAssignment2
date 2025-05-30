package com.example.scheduler.lv2.repository;

import com.example.scheduler.lv2.dto.EventResponseDto;
import com.example.scheduler.lv2.entitiy.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

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
    public EventResponseDto saveEvent(Event event) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("event")
                .usingGeneratedKeyColumns("id")
                .usingColumns("username", "password", "title", "contents");;

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username", event.getUsername());
        parameters.put("password", event.getPassword());
        parameters.put("title", event.getTitle());
        parameters.put("contents", event.getContents());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new EventResponseDto(key.longValue(), event.getUsername(), event.getTitle(), event.getContents());
    }

    @Override
    public List<EventResponseDto> findAllFilteredEvent(String username, LocalDate modified_at) {
        String sql = "SELECT * FROM event";
        List<EventResponseDto> result;

        if (username != null && modified_at != null) {
            sql += " WHERE username = ? AND DATE(modified_at) = ? ORDER BY modified_at desc";
            result = jdbcTemplate.query(sql, EventRowMapper(), username, modified_at);
        }
        else if (username != null) {
            sql += " WHERE username = ? ORDER BY modified_at desc";
            result = jdbcTemplate.query(sql, EventRowMapper(), username);
        }
        else if (modified_at != null) {
            sql += " WHERE DATE(modified_at) = ? ORDER BY modified_at desc";
            result = jdbcTemplate.query(sql, EventRowMapper(), modified_at);
        }
        else {
            sql += " ORDER BY modified_at desc";
            result = jdbcTemplate.query(sql, EventRowMapper());
        }

        return result;
    }

    @Override
    public Event findEventByIdOrElseThrow(Long id) {
        String sql = "SELECT * FROM event WHERE id = ?";
        List<Event> result = jdbcTemplate.query(sql, EventRowMapperV2(), id);

        return result.stream().findAny().orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Does not exist id = " + id
                )
        );
    }

    @Override
    public int updateUsernameOrContents(Long id, String username, String contents) {
        String sql = "UPDATE event SET";

        if (username != null && contents != null)
            return jdbcTemplate.update(sql + " username = ?, contents = ? WHERE id = ?", username, contents, id);
        else if (username != null)
            return jdbcTemplate.update(sql + " username = ? WHERE id = ?", username, id);
        else
            return jdbcTemplate.update(sql + " contents = ? WHERE id = ?", contents, id);
    }

    @Override
    public void deleteEvent(Long id) {
        jdbcTemplate.update("delete from event where id = ?", id);
    }

    private RowMapper<EventResponseDto> EventRowMapper() {
        return new RowMapper<EventResponseDto>() {
            @Override
            public EventResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new EventResponseDto(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("title"),
                        rs.getString("contents")
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
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("title"),
                        rs.getString("contents")
                );
            }

        };
    }
}
