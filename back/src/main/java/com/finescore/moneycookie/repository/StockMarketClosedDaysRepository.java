package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.ClosedDay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
public class StockMarketClosedDaysRepository {
    private final NamedParameterJdbcTemplate template;

    public StockMarketClosedDaysRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<ClosedDay> findAll() {
        String sql = "select date, name, type from closed_days";

        return template.query(sql, closedDayRowMapper());
    }

    public List<ClosedDay> findByDate(LocalDate date) {
        String sql = "select date, name, type from closed_days where date = :date";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("date", date);

        return template.queryForList(sql, param, ClosedDay.class);
    }

    public void save(List<ClosedDay> dayList) {
        deleteChangeableDays();

        List<ClosedDay> lastClosedList = findAll();

        for (ClosedDay changeDay : dayList) {
            if (isChangeableDay(changeDay)) {
                saveChangeableDays(changeDay);
                continue;
            }

            for (ClosedDay lastDay : lastClosedList) {
                if (isSameName(changeDay, lastDay)) {
                    updateDateOfClosedDay(changeDay);
                    break;
                }
            }
        }
    }

    private boolean isSameName(ClosedDay changeDay, ClosedDay lastDay) {
        return changeDay.getName().equals(lastDay.getName());
    }

    private boolean isChangeableDay(ClosedDay changeDay) {
        return changeDay.getName().equals("대체공휴일") || changeDay.getName().contains("선거");
    }

    private void updateDateOfClosedDay(ClosedDay changeDay) {
        String update_sql = "update closed_days set date = :date where name = :name";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("date", changeDay.getDate())
                .addValue("name", changeDay.getName());

        template.update(update_sql, param);
    }

    private void saveChangeableDays(ClosedDay changeDay) {
        String insert_sql = "insert into closed_days(date, name, type) values (:date, :name, :type)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("date", changeDay.getDate())
                .addValue("name", changeDay.getName())
                .addValue("type", changeDay.getType().toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(insert_sql, param, keyHolder);
    }

    private void deleteChangeableDays() {
        String delete_sql = "delete from closed_days where name = :substitute or name like :election";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("substitute", "대체공휴일")
                .addValue("election", "%선거%");

        template.update(delete_sql, param);
    }

    private RowMapper<ClosedDay> closedDayRowMapper() {
        return BeanPropertyRowMapper.newInstance(ClosedDay.class);
    }
}
