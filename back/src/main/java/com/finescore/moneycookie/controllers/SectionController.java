package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.PriceToTicker;
import com.finescore.moneycookie.models.ResponseMessage;
import com.finescore.moneycookie.models.SectionBuyInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SectionController {

    @PostMapping("/section/save")
    public ResponseMessage saveUserSectionInfo(SectionBuyInfo info) {


        ResponseMessage respMessage = new ResponseMessage<>("OK");
        return respMessage;
    }

    @PostMapping("/section/delete")
    public ResponseMessage deleteUserSectionInfo() {


        ResponseMessage respMessage = new ResponseMessage<>("OK");
        return respMessage;
    }
}
