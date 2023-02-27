package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.Holding;

import java.util.List;

public interface HoldingRepository {
    List<Holding> findBySectionId(Long sectionId);

    Long save(Holding holding);

    void update(Holding newHolding);

    void delete(Long holdingId);
}
