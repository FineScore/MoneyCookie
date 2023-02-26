package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.services.Evaluation;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;

public class EvaluationRepository {
    private final NamedParameterJdbcTemplate template;

    public EvaluationRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public void save(Evaluation evaluation) {
        String sql = "insert into evaluations(holding_id, evaluation_rate, evaluation_amount) " +
                "values(:holdingId, :evaluationRate, :evaluationAmount)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(evaluation);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);
    }

    public void update(Evaluation newEval) {
        String sql = "update evaluations set evaluation_rate = :evaluationRate, evaluation_amount = :evaluationAmount " +
                "where holding_id = :holdingId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("evaluationRate", newEval.getEvaluationRate())
                .addValue("evaluationAmount", newEval.getEvaluationAmount())
                .addValue("holdingId", newEval.getHoldingId());

        template.update(sql, param);
    }


}
