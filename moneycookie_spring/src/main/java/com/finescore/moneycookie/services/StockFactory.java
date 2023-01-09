package com.finescore.moneycookie.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finescore.moneycookie.models.Price;
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
public class StockFactory {
    public String getNowPrice(String ticker) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Price> priceList = new ArrayList<>();
        String url = "https://fchart.stock.naver.com/sise.nhn?timeframe=day&count=6000&requestType=0&symbol=005930";

        Document document = builder.parse(url);
        document.getDocumentElement().normalize();
        NodeList list = document.getElementsByTagName("item");
        for (int i = 0; i < list.getLength(); i++) {
            String[] lists = list.item(i).getAttributes().getNamedItem("data").getNodeValue().split("\\|");
            Price price = new Price(lists[0], Integer.parseInt(lists[4]));
            priceList.add(price);
        }

        return mapper.writeValueAsString(priceList);
    }
}
