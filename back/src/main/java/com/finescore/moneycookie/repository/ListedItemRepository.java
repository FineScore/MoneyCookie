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
import java.util.List;
import java.util.Optional;

@Slf4j
public class ListedItemRepository {
    private final NamedParameterJdbcTemplate template;

    public ListedItemRepository(DataSource dataSource) {
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

    public List<Item> findAll() {
        String sql = "select ticker, item_name, market from items_kr";

        return template.query(sql, itemRowMapper());
    }

    public Item findByItemKrId(Long itemKrId) {
        String sql = "select i.id, i.ticker, i.item_name, i.market, h.buy_date from items_kr i join holdings h on i.id = h.item_kr_id where i.id = :id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", itemKrId);

        return template.queryForObject(sql, param, itemRowMapper());
    }

    public Optional<List<Item>> findByKeyword(String keyword) {
        String sql = "select id, ticker, item_name, market from items_kr ";
        StringBuilder builder = new StringBuilder(sql);

        if (keyword.isBlank()) {
            return Optional.empty();
        }

        if (keyword.matches("\\d+")) {
            log.info("숫자");
            builder.append("where ticker like '")
                    .append(keyword)
                    .append("%'");
        } else {
            log.info("문자");
            builder.append("where item_name like '")
                    .append(keyword)
                    .append("%'");
        }

        return Optional.of(template.query(builder.toString(), itemRowMapper()));
    }

    private RowMapper<Item> itemRowMapper() {
        return BeanPropertyRowMapper.newInstance(Item.class);
    }
}
