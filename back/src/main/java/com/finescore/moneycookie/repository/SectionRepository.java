package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.Section;

import java.util.List;

public interface SectionRepository {
    List<Section> findByUsername(String username);

    Long save(Section section);

    void delete(Long sectionId);

    void update(Long sectionId, String newTitle);
}
