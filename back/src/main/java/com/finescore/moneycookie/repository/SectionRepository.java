package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.Section;

import java.time.LocalDateTime;
import java.util.List;

public interface SectionRepository {
    List<Section> findByUsername(String username);

    Long save(String username, String title, LocalDateTime savedTime);

    void delete(Long sectionId);

    void update(Long sectionId, String newTitle);
}
