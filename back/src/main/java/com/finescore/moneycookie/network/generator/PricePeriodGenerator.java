package com.finescore.moneycookie.network.generator;

import com.finescore.moneycookie.models.Item;
import com.finescore.moneycookie.services.PriceToDate;
import com.finescore.moneycookie.services.PriceToTicker;
import com.finescore.moneycookie.network.parser.Parser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

@Component
@Slf4j
public class PricePeriodGenerator extends PriceAllGenerator {
    public PricePeriodGenerator(Parser XMLParser) {
        super(XMLParser);
    }

    @Override
    public PriceToTicker getPrice(Item info) {
        List<PriceToDate> list = getList(info);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        for (int i = list.size() - 1; i >= 0; i--) {
            log.info("구매 일자: {}, {}", info.getBuyDate(), list.get(i).getDate());
            if (dateFormat.format(info.getBuyDate()).equals(dateFormat.format(list.get(i).getDate()))) {
                list = list.subList(i, list.size());
                break;
            }
        }

        return new PriceToTicker(info.getTicker(), list);
    }
}
