package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class HoldingRepositoryJdbc implements HoldingRepository {
    private final NamedParameterJdbcTemplate template;

    public HoldingRepositoryJdbc(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Holding> findBySectionId(Long sectionId) {
        String sql = "select h.id h_id, h.section_id, h.item_kr_id, i.ticker, i.item_name, h.quantity, h.buy_avg_price, h.buy_total_amount, h.buy_date, " +
                "e.id e_id, e.holding_id, e.evaluation_rate, e.evaluation_amount " +
                "from holdings h join evaluations e on h.id = e.holding_id " +
                "join items_kr i on h.item_kr_id = i.id " +
                "where h.section_id = :sectionId";

        Map<String, Long> holdingParam = Map.of("sectionId", sectionId);

        return template.query(sql, holdingParam, holdingRowMapper());
    }

    public Long save(InsertHolding insertHolding, Long totalAmount) {
        String sql = "insert into holdings(section_id, item_kr_id, quantity, buy_avg_price, buy_total_amount, buy_date) " +
                "values(:sectionId, :itemKrId, :quantity, :buyAvgPrice, :buyTotalAmount, :buyDate)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("sectionId", insertHolding.getSectionId())
                .addValue("itemKrId", insertHolding.getItemKrId())
                .addValue("quantity", insertHolding.getQuantity())
                .addValue("buyAvgPrice", insertHolding.getBuyAvgPrice())
                .addValue("buyTotalAmount", totalAmount)
                .addValue("buyDate", insertHolding.getBuyDate());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(sql, param, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void update(UpdateHolding updateHolding, Long totalAmount) {
        StringBuilder builder = new StringBuilder("update holdings set ");
        MapSqlParameterSource param = new MapSqlParameterSource();

        if (updateHolding.getQuantity() != null) {
            builder.append("quantity = :quantity, ");
            param.addValue("quantity", updateHolding.getQuantity());
        }

        if (updateHolding.getBuyAvgPrice() != null) {
            builder.append("buy_avg_price = :buyAvgPrice, buy_total_amount = :buyTotalAmount ");
            param.addValue("buyAvgPrice", updateHolding.getBuyAvgPrice())
                    .addValue("buyTotalAmount", totalAmount);
        }

        builder.append("where id = :id");
        param.addValue("id", updateHolding.getId());

        template.update(builder.toString(), param);
    }

    public void delete(Long holdingId) {
        String sql = "delete from holdings where id = :id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", holdingId);

        template.update(sql, param);
    }

    private RowMapper<Holding> holdingRowMapper() {
        return (rs, rowNum) -> Holding.builder()
                .id(rs.getLong("h_id"))
                .sectionId(rs.getLong("section_id"))
                .itemKrId(rs.getLong("item_kr_id"))
                .ticker(rs.getString("ticker"))
                .itemName(rs.getString("item_name"))
                .quantity(rs.getInt("quantity"))
                .buyAvgPrice(rs.getInt("buy_avg_price"))
                .buyTotalAmount(rs.getLong("buy_total_amount"))
                .buyDate(rs.getDate("buy_date"))
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
