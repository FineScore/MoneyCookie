package com.finescore.moneycookie.network.generator;

import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.PriceToDate;
import com.finescore.moneycookie.network.factory.PriceRequestFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
abstract class PriceGenerator<T> implements Generator<T> {
    private final PriceRequestFactory factory;

    List<PriceToDate> parse(ItemInfo info) {
        Document response = factory.request(info);
        response.getDocumentElement().normalize();
        NodeList list = response.getElementsByTagName("item");

        return getPriceList(list);
    }

    private List<PriceToDate> getPriceList(NodeList list) {
        List<PriceToDate> priceList = new ArrayList<>();

        for (int i = 0; i < list.getLength(); i++) {
            String[] lists = list.item(i).getAttributes().getNamedItem("data").getNodeValue().split("\\|");
            PriceToDate price = new PriceToDate(LocalDate.parse(lists[0], DateTimeFormatter.ofPattern("yyyyMMdd")), Integer.parseInt(lists[4]));
            priceList.add(price);
        }

        return priceList;
    }
}
