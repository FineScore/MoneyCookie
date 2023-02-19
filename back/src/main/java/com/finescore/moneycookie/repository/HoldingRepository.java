package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.Holding;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;

public class HoldingRepository {
    private final NamedParameterJdbcTemplate template;

    public HoldingRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public Long save(Holding holding) {
        String sql = "insert into holdings(section_id, item_kr_id, quantity, buy_avg_price, buy_total_amount, buy_date) " +
                "values(:sectionId, :itemKrId, :quantity, :buyAvgPrice, :buyTotalAmount, :buyDate)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("sectionId", holding.getSectionId())
                .addValue("itemKrId", holding.getItemKrId())
                .addValue("quantity", holding.getQuantity())
                .addValue("buyAvgPrice", holding.getBuyAvgPrice())
                .addValue("buyTotalAmount", holding.getBuyTotalAmount())
                .addValue("buyDate", holding.getBuyDate());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(sql, param, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void update(Holding newHolding) {
        String sql = "update holdings set ";
        StringBuilder builder = new StringBuilder(sql);
        MapSqlParameterSource param = new MapSqlParameterSource();

        if (newHolding.getQuantity() != null) {
            builder.append("quantity = :quantity, ");
            param.addValue("quantity", newHolding.getQuantity());
        }

        if (newHolding.getBuyAvgPrice() != null) {
            builder.append("buy_avg_price = :buyAvgPrice, buy_total_amount = :buyTotalAmount, ");
            param.addValue("buyAvgPrice", newHolding.getBuyAvgPrice())
                    .addValue("buyTotalAmount", newHolding.getBuyTotalAmount());
        }

        builder.append("where section_id = :sectionId and item_kr_id = :itemKrId");
        param.addValue("sectionId", newHolding.getSectionId())
                .addValue("itemKrId", newHolding.getItemKrId());

        template.update(sql, param);
    }

    public void delete(Holding holding) {
        String sql = "delete from holdings where id = :id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", holding.getId());

        template.update(sql, param);
    }
}
