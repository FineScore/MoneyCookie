package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.models.Section;
import com.finescore.moneycookie.services.InfoService;
import com.finescore.moneycookie.services.SectionService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
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
@Api(tags = "보유종목 관련 API")
@RequestMapping("/section")
public class SectionController {
    private final SectionService service;
    private final InfoService infoService;

    @Operation(summary = "회원이 등록한 모든 보유종목 조회")
    @GetMapping
    public ResponseEntity<DataResponse> findAll(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Section> sectionList = service.findByUsername(
                String.valueOf(session.getAttribute("username"))
        );

        DataResponse dataResponse = new DataResponse("SUCCESS", "조회 성공", sectionList);
        log.info("섹션 조회 성공");

        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @Operation(summary = "보유종목 저장")
    @PostMapping
    public ResponseEntity<MessageResponse> save(@RequestBody Section section, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = String.valueOf(session.getAttribute("username"));

        service.save(username, section.getTitle(), section.getHoldingList());

        MessageResponse messageResponse = new MessageResponse("SUCCESS", "저장 완료");
        log.info("섹션 저장 완료");
        infoService.saveStockMarketClosedDays();

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @Operation(summary = "보유종목 수정")
    @PutMapping
    public ResponseEntity<MessageResponse> update(@RequestBody Section section) {
        service.updateSection(section);

        MessageResponse messageResponse = new MessageResponse("SUCCESS", "수정 완료");
        log.info("섹션 수정 완료");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @Operation(summary = "보유종목 삭제")
    @DeleteMapping
    public ResponseEntity<MessageResponse> delete(Long sectionId) {
        service.delete(sectionId);

        MessageResponse messageResponse = new MessageResponse("SUCCESS", "삭제 완료");
        log.info("섹션 삭제 완료");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
}
