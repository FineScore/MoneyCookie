package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.TotalRating;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;

public class TotalRatingRepository {
    private final NamedParameterJdbcTemplate template;

    public TotalRatingRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public void save(TotalRating totalRating) {
        String sql = "insert into total_ratings (section_id, total_asset, total_evaluation_rate, total_evaluation_amount) " +
                "values(:sectionId, :totalAsset, :totalEvaluationRate, :totalEvaluationAmount)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(totalRating);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);
    }

    public void update(TotalRating oldTotalRating, TotalRating newTotalRating) {
        String sql = "update total_ratings set total_asset = :totalAsset, " +
                "total_evaluation_rate = :totalEvaluationRate, " +
                "total_evaluation_amount = :totalEvaluationAmount " +
                "where section_id = :sectionId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("totalAsset", newTotalRating.getTotalAsset())
                .addValue("totalEvaluationRate", newTotalRating.getTotalEvaluationRate())
                .addValue("total_evaluation_amount", newTotalRating.getTotalEvaluationAmount())
                .addValue("sectionId", oldTotalRating.getSectionId());

        template.update(sql, param);
    }
}
