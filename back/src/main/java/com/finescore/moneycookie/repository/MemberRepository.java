package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.MemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

public class MemberRepository {
    private final NamedParameterJdbcTemplate template;

    public MemberRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public MemberInfo save(MemberInfo info) {
        String sql = "insert into member(email, password, nickname, create_date) " +
                "values(:email, :password, :nickname, :createDate)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(info);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        Long key = keyHolder.getKey().longValue();
        info.setId(key);
        return info;
    }

    public Optional<MemberInfo> findByEmail(String email) {
        String sql = "select id, password, email, nickname, create_date from member where email=:email";

        try {
            Map<String, String> param = Map.of("email", email);
            MemberInfo info = template.queryForObject(sql, param, memberRowMapper());

            return Optional.of(info);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void update(Long id, String nickname) {
        String sql = "update member set nickname=:nickname where id=:id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("nickname", nickname)
                .addValue("id", id);

        template.update(sql, param);
    }

    public void delete(Long id) {
        String sql = "delete from member where id=:id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);

        template.update(sql, param);
    }

    private RowMapper<MemberInfo> memberRowMapper() {
        return BeanPropertyRowMapper.newInstance(MemberInfo.class);
    }
}
