package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.HoldingInfo;
import com.finescore.moneycookie.models.SectionInfo;
import com.finescore.moneycookie.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionService {
    private final SectionRepository sectionRepository;

    public List<SectionInfo> findById(Long memberId) {
        return sectionRepository.findById(memberId);
    }

    public SectionInfo saveSection(SectionInfo info) {
        return sectionRepository.saveSection(info);
    }

    public HoldingInfo saveHolding(HoldingInfo info) {
        return sectionRepository.saveHolding(info);
    }

    public void updateSection(SectionInfo info, String title) {
        sectionRepository.updateSection(info, title);
    }

    public void updateHoldings(SectionInfo info, List<HoldingInfo> updateHoldings) {
        sectionRepository.updateHolding(info, updateHoldings);
    }

    public void delete(Long sectionId) {
        sectionRepository.deleteSection(sectionId);
    }
}
