package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.services.PriceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StockController {
    private PriceService userService;

}
