package com.example.scheduler.lv3.repository;

import com.example.scheduler.lv3.dto.EventResponseDto;
import com.example.scheduler.lv3.entitiy.Event;
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
    public List<Event> findAllFilteredEvent(Long user_id, LocalDate modified_at) {
        String sql = "SELECT * FROM event";
        List<Event> result;

        if (user_id != null && modified_at != null) {
            sql += " WHERE user_id = ? AND DATE(modified_at) = ? ORDER BY modified_at desc";
            result = jdbcTemplate.query(sql, EventRowMapperV2(), user_id, modified_at);
        }
        else if (user_id != null) {
            sql += " WHERE user_id = ? ORDER BY modified_at desc";
            result = jdbcTemplate.query(sql, EventRowMapperV2(), user_id);
        }
        else if (modified_at != null) {
            sql += " WHERE DATE(modified_at) = ? ORDER BY modified_at desc";
            result = jdbcTemplate.query(sql, EventRowMapperV2(), modified_at);
        }
        else {
            sql += " ORDER BY modified_at desc";
            result = jdbcTemplate.query(sql, EventRowMapperV2());
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
                        "Does not exist event_id = " + id
                )
        );
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

//    private RowMapper<EventResponseDto> EventRowMapper() {
//        return new RowMapper<EventResponseDto>() {
//            @Override
//            public EventResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return new EventResponseDto(
//                        rs.getLong("id"),
//                        rs.getLong("user_id"),
//                        rs.getString("title"),
//                        rs.getString("contents")
//                );
//            }
//
//        };
//    }

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
