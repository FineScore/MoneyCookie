package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.*;
import com.mysql.cj.result.Row;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.ArrayList;
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
        String find_section_sql = "select s.id, s.username, s.title, " +
                "tr.id tr_id, tr.section_id, tr.total_asset, tr.total_evaluation_rate, tr.total_evaluation_amount " +
                "from sections s join total_ratings tr on s.id = tr.section_id " +
                "where username = :username";
        String find_holding_sql = "select h.id h_id, h.section_id, h.item_kr_id, i.item_name, h.quantity, h.buy_avg_price, h.buy_total_amount, " +
                "e.id e_id, e.holding_id, e.evaluation_rate, e.evaluation_amount " +
                "from holdings h join evaluations e on h.id = e.holding_id " +
                "join items_kr i on h.item_kr_id = i.id " +
                "where h.section_id = :sectionId";

        Map<String, String> sectionParam = Map.of("username", username);

        List<Section> sectionList = template.query(find_section_sql, sectionParam, sectionRowMapper());

        for (Section section : sectionList) {
            Map<String, Long> holdingParam = Map.of("sectionId", section.getId());

            List<Holding> holdingList = template.query(find_holding_sql, holdingParam, holdingRowMapper());

            section.setHoldingList(holdingList);
        }

        return Optional.of(sectionList);
    }

    public void delete(Long sectionId) {
        String sql = "delete from sections where id = :id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", sectionId);

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
        return (rs, rowNum) ->
                Section.builder()
                        .id(rs.getLong("id"))
                        .username(rs.getString("username"))
                        .title(rs.getString("title"))
                        .totalRating(
                                TotalRating.builder()
                                        .id(rs.getLong("tr_id"))
                                        .sectionId(rs.getLong("section_id"))
                                        .totalAsset(rs.getLong("total_asset"))
                                        .totalEvaluationRate(rs.getDouble("total_evaluation_rate"))
                                        .totalEvaluationAmount(rs.getLong("total_evaluation_amount"))
                                        .build()
                        )
                        .build();
    }

    private RowMapper<Holding> holdingRowMapper() {
        return (rs, rowNum) -> Holding.builder()
                .id(rs.getLong("h_id"))
                .sectionId(rs.getLong("section_id"))
                .itemKrId(rs.getLong("item_kr_id"))
                .itemName(rs.getString("item_name"))
                .quantity(rs.getInt("quantity"))
                .buyAvgPrice(rs.getInt("buy_avg_price"))
                .buyTotalAmount(rs.getLong("buy_total_amount"))
                .evaluation(
                        Evaluation.builder()
                                .id(rs.getLong("e_id"))
                                .holdingId(rs.getLong("holding_id"))
                                .evaluationRate(rs.getDouble("evaluation_rate"))
                                .evaluationAmount(rs.getLong("evaluation_amount"))
                                .build()
                )
                .build();
    }
}
