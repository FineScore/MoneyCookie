package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.ItemInfo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ItemRepository {
    private final NamedParameterJdbcTemplate template;

    public ItemRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<ItemInfo> save(List<ItemInfo> infoList) {
        String sql = "insert into items_kr(ticker, item_name, market) values (:ticker, :itemName, :market)";

        for (ItemInfo info : infoList) {
            SqlParameterSource param = new BeanPropertySqlParameterSource(info);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(sql, param, keyHolder);

            Long key = keyHolder.getKey().longValue();
            info.setId(key);
        }

        return infoList;
    }

    public List<ItemInfo> findAll() {
        String sql = "select id, ticker, item_name, market from items_kr";

        return template.query(sql, itemRowMapper());
    }

    public Optional<List<ItemInfo>> findSearch(String keyword) {
        String sql = "select id, ticker, item_name, market from items_kr where ticker like '" + keyword + "%' or item_name like '" + keyword + "%'";

        try {
            List<ItemInfo> list = template.query(sql, itemRowMapper());

            return Optional.of(list);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<ItemInfo> itemRowMapper() {
        return BeanPropertyRowMapper.newInstance(ItemInfo.class);
    }
}
