package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.services.TotalRating;

public interface TotalRatingRepository {
    TotalRating findBySectionId(Long sectionId);

    void save(TotalRating totalRating);

    void update(TotalRating totalRating);
}
