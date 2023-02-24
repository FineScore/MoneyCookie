package com.finescore.moneycookie.network.generator;

import com.finescore.moneycookie.models.Item;
import com.finescore.moneycookie.models.PriceToDate;
import com.finescore.moneycookie.network.parser.Parser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public abstract class PriceAllGenerator implements PriceGenerator {
    private final Parser<Document> XMLParser;
    private final String URL = "https://fchart.stock.naver.com/sise.nhn?timeframe=day&count=6000&requestType=0&symbol=%s";

    protected List<PriceToDate> getList(Item info) {
        Document body = XMLParser.parse(setURL(URL, info.getTicker()));
        NodeList list = body.getElementsByTagName("item");

        return getPriceToList(list);
    }

    private List<PriceToDate> getPriceToList(NodeList list) {
        List<PriceToDate> priceList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        for (int i = 0; i < list.getLength(); i++) {
            String[] lists = list.item(i).getAttributes().getNamedItem("data").getNodeValue().split("\\|");
            PriceToDate price = null;
            try {
                price = new PriceToDate(dateFormat.parse(lists[0]), Integer.parseInt(lists[4]));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            priceList.add(price);
        }
        return priceList;
    }

    private String setURL(String url, Object object) {
        return String.format(url, object);
    }
}