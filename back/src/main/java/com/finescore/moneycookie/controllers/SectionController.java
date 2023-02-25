package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.models.Section;
import com.finescore.moneycookie.services.SectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/section")
public class SectionController {
    private final SectionService service;

    @GetMapping
    public ResponseEntity<List<Section>> findAll(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Section> sectionList = service.findByUsername(
                String.valueOf(session.getAttribute("username"))
        );

        return new ResponseEntity<>(sectionList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> save(
            @RequestBody Section section,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        String username = String.valueOf(session.getAttribute("username"));
        service.save(username, section.getTitle(), section.getHoldingList());

        return new ResponseEntity<>("보유종목 저장 완료", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Section section) {
        log.info(section.getHoldingList().toString());
        service.updateSection(section);

        return new ResponseEntity<>("보유종목 수정 완료", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(Long sectionId) {
        service.delete(sectionId);

        return new ResponseEntity<>("보유종목 삭제 완료", HttpStatus.OK);
    }
}
