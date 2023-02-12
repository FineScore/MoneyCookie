package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.ClosedDay;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.List;

public class ClosedDayRepository {
    private final NamedParameterJdbcTemplate template;

    public ClosedDayRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<ClosedDay> findAll() {
        String sql = "select id, date, name, type from closed_day";

        return template.query(sql, closedDayRowMapper());
    }

    public List<ClosedDay> save(List<ClosedDay> dayList) {
        String sql = "insert into closed_day(date, name, type) values (:date, :name, :type)";

        for (ClosedDay day : dayList) {
            SqlParameterSource param = new BeanPropertySqlParameterSource(day);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(sql, param, keyHolder);

            Long key = keyHolder.getKey().longValue();
            day.setId(key);
        }

        return dayList;
    }

    private RowMapper<ClosedDay> closedDayRowMapper() {
        return BeanPropertyRowMapper.newInstance(ClosedDay.class);
    }
}
