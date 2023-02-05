package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.models.ResponseMessage;
import com.finescore.moneycookie.models.HoldingInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SectionController {

    @PostMapping("/section/save")
    public ResponseMessage saveUserSectionInfo(HoldingInfo info) {


        ResponseMessage respMessage = new ResponseMessage<>("OK");
        return respMessage;
    }

    @PostMapping("/section/delete")
    public ResponseMessage deleteUserSectionInfo() {


        ResponseMessage respMessage = new ResponseMessage<>("OK");
        return respMessage;
    }
}
