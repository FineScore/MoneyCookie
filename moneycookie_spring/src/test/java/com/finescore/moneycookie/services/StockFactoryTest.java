package com.finescore.moneycookie.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.util.ArrayList;

class StockFactoryTest {

    @Test
    void getNowPrice() throws ParserConfigurationException, IOException, SAXException {

        // Given
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Price> priceList = new ArrayList<>();
        String url = "https://fchart.stock.naver.com/sise.nhn?timeframe=day&count=6000&requestType=0&symbol=005930";

        // When
        Document document = builder.parse(url);
        document.getDocumentElement().normalize();
        NodeList list = document.getElementsByTagName("item");
        for (int i = 0; i < list.getLength(); i++) {
            String[] lists = list.item(i).getAttributes().getNamedItem("data").getNodeValue().split("\\|");
            Price price = new Price(lists[0], Integer.parseInt(lists[4]));
            priceList.add(price);
        }
        System.out.println(mapper.writeValueAsString(priceList).getClass().getName());
    }
}

class Price {
    String date;
    int price;

    public Price(String date, int price) {
        this.date = date;
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}