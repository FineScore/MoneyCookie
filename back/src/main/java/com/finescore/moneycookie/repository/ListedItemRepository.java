package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.Item;

import java.util.List;
import java.util.Optional;

public interface ListedItemRepository {
    void save(List<Item> infoList);

    Item findByHoldingId(Long holdingId);

    List<Item> findByKeyword(String keyword);
}
