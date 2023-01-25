package com.finescore.moneycookie.network.generator;

import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.PriceToDate;
import com.finescore.moneycookie.network.factory.RequestFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public abstract class PriceGenerator<T> implements Generator<T> {
    private final RequestFactory<Document, ItemInfo> priceRequestFactory;

    protected List<PriceToDate> parse(ItemInfo info) {
        Document response = priceRequestFactory.request(info);
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
