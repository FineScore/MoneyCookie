package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.Holding;
import com.finescore.moneycookie.models.InsertHolding;
import com.finescore.moneycookie.models.UpdateHolding;

import java.util.List;

public interface HoldingRepository {
    List<Holding> findBySectionId(Long sectionId);

    Long save(InsertHolding insertHolding, Long totalAmount);

    void update(UpdateHolding updateHolding, Long totalAmount);

    void delete(Long holdingId);
}
