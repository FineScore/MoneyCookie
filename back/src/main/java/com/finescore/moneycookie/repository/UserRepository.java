package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class UserRepository {
    private final NamedParameterJdbcTemplate template;

    public UserRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<User> findByUsername(String username) {
        String sql = "select username, password from users where username = :username";

        Map<String, String> param = Map.of("username", username);

        return template.query(sql, param, userRowMapper());
    }

    public List<String> findForDuplicateCheck(String username) {
        String sql = "select username from users where username = :username";

        Map<String, String> param = Map.of("username", username);

        return template.queryForList(sql, param, String.class);
    }

    public void save(User member) {
        String sql = "insert into users (username, password, create_date) " +
                "values (:username, :password, :createDate)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("username", member.getUsername())
                .addValue("password", member.getPassword())
                .addValue("createDate", LocalDateTime.now());

        template.update(sql, param);
    }

    public void update(String username, String newPassword) {
        String sql = "update users set password = :password where username = :username";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("username", username)
                .addValue("password", newPassword);

        template.update(sql, param);
    }

    public void delete(String username) {
        String sql = "delete from users where username = :username";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("username", username);

        template.update(sql, param);
    }

    private RowMapper<User> userRowMapper() {
        return BeanPropertyRowMapper.newInstance(User.class);
    }
}
