package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SectionRepository {
    private final NamedParameterJdbcTemplate template;

    public SectionRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public Long save(Section section) {
        String sql = "insert into sections (username, title, create_date) " +
                "values (:username, :title, :createDate)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(section);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(sql, param, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Optional<List<Section>> findByUsername(String username) {
        String sql = "select username, title, " +
                "(select id, section_id, total_asset, total_evaluation_rate, total_evaluation_amount " +
                "from total_ratings where sections.id = total_ratings.section_id), " +
                "(select id, section_id, item_kr_id, quantity, buy_avg_price, buy_total_amount " +
                "from holdings where sections.id = holdings.section_id), " +
                "(select id, holding_id, evaluation_rate, evaluation_amount " +
                "from evaluations where holdings.id = evaluations.holding_id) " +
                "from sections where username = :username";

        Map<String, String> param = Map.of("username", username);

        return Optional.of(template.query(sql, param, sectionRowMapper()));
    }

    public void delete(Section section) {
        String sql = "delete from sections where id = :id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", section.getId());

        template.update(sql, param);
    }

    public void update(Long sectionId, String newTitle) {
        String sql = "update sections set title = :title where id = :id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("title", newTitle)
                .addValue("id", sectionId);

        template.update(sql, param);
    }

    private RowMapper<Section> sectionRowMapper() {
        return BeanPropertyRowMapper.newInstance(Section.class);
    }
}
