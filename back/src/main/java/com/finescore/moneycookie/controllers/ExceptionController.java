package com.finescore.moneycookie.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ExceptionController {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageResponse> exceptionHandle(RuntimeException e) {
        MessageResponse messageResponse = new MessageResponse("ERROR", "서버 내부 에러");

        log.error("에러 발생: ", e);

        return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
