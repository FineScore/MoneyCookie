package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.HoldingInfo;
import com.finescore.moneycookie.models.SectionInfo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SectionRepository {
    private final NamedParameterJdbcTemplate template;

    public SectionRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public SectionInfo saveSection(SectionInfo info) {
        String sql = "insert into section(member_id, section_num, title, create_date) " +
                "values(:memberId, :sectionNum, :title, :createDate)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(info);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        Long key = keyHolder.getKey().longValue();
        info.setId(key);
        return info;
    }

    public HoldingInfo saveHolding(HoldingInfo info) {
        String sql = "insert into holdings(section_id, item_kr_id, quantity, buy_price, buy_date) " +
                "values(:sectionId, :itemKrId, :quantity, :buyPrice, :buyDate)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(info);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        Long key = keyHolder.getKey().longValue();
        info.setId(key);
        return info;
    }

    public Optional<List<HoldingInfo>> findHoldingsById(Long id) {
        String sql = "select h.id as id, s.id as section_id, h.item_kr_id as item_kr_id, h.quantity as quantity, h.buy_price as buy_price, h.buy_date as buy_date from section s join holdings h on s.id=h.section_id where s.id=:id";

        try {
            Map<String, Long> param = Map.of("id", id);
            List<HoldingInfo> info = template.query(sql, param, holdingRowMapper());

            return Optional.of(info);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<SectionInfo> findSectionByMemberId(Long memberId) {
        String sql = "select id, member_id, section_num, title, create_date from section where member_id=:memberId";
        Map<String, Long> param = Map.of("memberId", memberId);

        return template.query(sql, param, sectionRowMapper());
    }

    public void deleteSection(Long sectionId) {
        String sql = "delete from section where id=:id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", sectionId);

        template.update(sql, param);
    }

    public void updateHolding(Long sectionId, List<HoldingInfo> updateHoldings) {
        String sql = "update holdings set item_kr_id=:itemKrId, quantity=:quantity, buy_price=:buyPrice, buy_date=:buyDate where id=:id and section_id=:sectionId";

        for (HoldingInfo info: updateHoldings) {
            SqlParameterSource param = new MapSqlParameterSource()
                    .addValue("itemKrId", info.getItemKrId())
                    .addValue("quantity", info.getQuantity())
                    .addValue("buyPrice", info.getBuyPrice())
                    .addValue("buyDate", info.getBuyDate())
                    .addValue("id", info.getId())
                    .addValue("sectionId", sectionId);

            template.update(sql, param);
        }
    }

    private RowMapper<SectionInfo> sectionRowMapper() {
        return BeanPropertyRowMapper.newInstance(SectionInfo.class);
    }

    private RowMapper<HoldingInfo> holdingRowMapper() {
        return BeanPropertyRowMapper.newInstance(HoldingInfo.class);
    }
}
