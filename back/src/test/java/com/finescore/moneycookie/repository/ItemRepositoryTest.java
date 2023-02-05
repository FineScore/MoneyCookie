package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.ItemInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@Sql("classpath:reset.sql")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상장 종목 정보 저장")
    void save() {
        ItemInfo info1 = new ItemInfo("010101", "종목1", "KOSPI");
        ItemInfo info2 = new ItemInfo("010102", "종목2", "KOSPI");
        List<ItemInfo> infoList = List.of(info1, info2);

        itemRepository.save(infoList);
        List<ItemInfo> list = itemRepository.findAll();

        assertThat(list)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(infoList);
    }

    @Test
    @DisplayName("상장 종목 검색")
    void findSearch() {
        ItemInfo info1 = new ItemInfo("010101", "종목1", "KOSPI");
        ItemInfo info2 = new ItemInfo("010102", "종목2", "KOSPI");
        List<ItemInfo> infoList = List.of(info1, info2);

        itemRepository.save(infoList);

        Optional<List<ItemInfo>> searchList1 = itemRepository.findSearch("0");
        Optional<List<ItemInfo>> searchList2 = itemRepository.findSearch("010102");
        Optional<List<ItemInfo>> searchList3 = itemRepository.findSearch("종");
        Optional<List<ItemInfo>> searchList4 = itemRepository.findSearch("종목2");

        assertThat(searchList1.get().get(0).getTicker()).isEqualTo("010101");
        assertThat(searchList1.get().get(1).getTicker()).isEqualTo("010102");
        assertThat(searchList2.get().get(0).getTicker()).isEqualTo("010102");
        assertThat(searchList3.get().get(0).getItemName()).isEqualTo("종목1");
        assertThat(searchList3.get().get(1).getItemName()).isEqualTo("종목2");
        assertThat(searchList4.get().get(0).getItemName()).isEqualTo("종목2");

    }
}