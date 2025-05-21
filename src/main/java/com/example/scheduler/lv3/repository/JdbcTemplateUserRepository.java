package com.example.scheduler.lv3.repository;

import com.example.scheduler.lv3.dto.EventResponseDto;
import com.example.scheduler.lv3.dto.UserResponseDto;
import com.example.scheduler.lv3.entitiy.Event;
import com.example.scheduler.lv3.entitiy.User;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;


    public JdbcTemplateUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User saveUser(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user")
                .usingGeneratedKeyColumns("id")
                .usingColumns("name", "email");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", user.getName());
        parameters.put("email", user.getEmail());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new User(key.longValue(), user.getName(), user.getEmail());
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        List<User> result = jdbcTemplate.query(sql, UserRowMapper(), email);

        return result.stream().findAny();
    }

    @Override
    public User findUserByIdOrElseThrow(Long id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        List<User> result = jdbcTemplate.query(sql, UserRowMapper(), id);

        return result.stream().findAny().orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Does not exist user_id = " + id
                )
        );
    }

    @Override
    public int updateName(Long id, String name) {
        String sql = "UPDATE user SET";

        return jdbcTemplate.update(sql + " name = ? WHERE id = ?", name, id);
    }

    private RowMapper<User> UserRowMapper() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }

        };
    }

}


