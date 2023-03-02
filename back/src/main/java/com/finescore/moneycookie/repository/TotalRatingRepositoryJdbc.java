package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.TotalRating;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;

public class TotalRatingRepositoryJdbc implements TotalRatingRepository {
    private final NamedParameterJdbcTemplate template;

    public TotalRatingRepositoryJdbc(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public TotalRating findBySectionId(Long sectionId) {
        String sql = "select id, section_id, total_asset, total_evaluation_rate, total_evaluation_amount " +
                "from total_ratings " +
                "where section_id = :sectionId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("sectionId", sectionId);

        return template.queryForObject(sql, param, totalRatingRowMapper());
    }

    public void save(TotalRating totalRating) {
        String sql = "insert into total_ratings (section_id, total_asset, total_evaluation_rate, total_evaluation_amount) " +
                "values(:sectionId, :totalAsset, :totalEvaluationRate, :totalEvaluationAmount)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(totalRating);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);
    }

    public void update(TotalRating totalRating) {
        String sql = "update total_ratings set total_asset = :totalAsset, " +
                "total_evaluation_rate = :totalEvaluationRate, " +
                "total_evaluation_amount = :totalEvaluationAmount " +
                "where section_id = :sectionId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("totalAsset", totalRating.getTotalAsset())
                .addValue("totalEvaluationRate", totalRating.getTotalEvaluationRate())
                .addValue("totalEvaluationAmount", totalRating.getTotalEvaluationAmount())
                .addValue("sectionId", totalRating.getSectionId());

        template.update(sql, param);
    }

    private RowMapper<TotalRating> totalRatingRowMapper() {
        return BeanPropertyRowMapper.newInstance(TotalRating.class);
    }
}
