package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.Item;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
public class ListedItemRepositoryJdbc implements ListedItemRepository {
    private final NamedParameterJdbcTemplate template;

    public ListedItemRepositoryJdbc(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public void save(List<Item> infoList) {
        String sql = "insert into items_kr(ticker, item_name, market) values (:ticker, :itemName, :market)";

        for (Item info : infoList) {
            SqlParameterSource param = new BeanPropertySqlParameterSource(info);
            KeyHolder keyHolder = new GeneratedKeyHolder();

            template.update(sql, param, keyHolder);
        }
    }

    public Item findByHoldingId(Long holdingId) {
        String sql = "select i.id, i.ticker, i.item_name, i.market, h.buy_date from items_kr i join holdings h on i.id = h.item_kr_id where h.id = :holdingId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("holdingId", holdingId);

        return template.queryForObject(sql, param, itemRowMapper());
    }

    public List<Item> findByKeyword(String keyword) {
        String sql = "select id, ticker, item_name, market from items_kr ";
        StringBuilder builder = new StringBuilder(sql);

        if (keyword.isBlank()) {
            return new ArrayList<>();
        }

        if (keyword.matches("\\d+")) {
            builder.append("where ticker like '")
                    .append(keyword)
                    .append("%'");
        } else {
            builder.append("where item_name like '")
                    .append(keyword)
                    .append("%'");
        }

        return template.query(builder.toString(), itemRowMapper());
    }

    private RowMapper<Item> itemRowMapper() {
        return BeanPropertyRowMapper.newInstance(Item.class);
    }
}
