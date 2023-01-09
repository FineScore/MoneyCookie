package com.finescore.moneycookie.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finescore.moneycookie.models.Price;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class StockFactory {
    private final ObjectMapper objectMapper;

    public ArrayList<Price> getTotalPrice(String ticker) throws ParserConfigurationException, IOException, SAXException {
        String priceUrl = "https://fchart.stock.naver.com/sise.nhn?timeframe=day&count=6000&requestType=0&symbol=%s";
        String url = String.format(priceUrl, ticker);
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        ArrayList<Price> priceList = new ArrayList<>();

        Document document = builder.parse(url);
        document.getDocumentElement().normalize();
        NodeList list = document.getElementsByTagName("item");

        for (int i = 0; i < list.getLength(); i++) {
            String[] lists = list.item(i).getAttributes().getNamedItem("data").getNodeValue().split("\\|");
            Price price = new Price(lists[0], Integer.parseInt(lists[4]));
            priceList.add(price);
        }

        return priceList;
    }

    public String getNowPrice(String ticker) throws ParserConfigurationException, IOException, SAXException {
        ArrayList<Price> totalPrice = getTotalPrice(ticker);

        return objectMapper.writeValueAsString(totalPrice.get(totalPrice.size() - 1));
    }
}
