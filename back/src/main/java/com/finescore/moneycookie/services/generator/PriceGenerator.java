package com.finescore.moneycookie.services.generator;

import com.finescore.moneycookie.models.PriceToDate;
import com.finescore.moneycookie.services.factory.PriceRequestFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PriceGenerator implements Generator<List<PriceToDate>>{
    private PriceRequestFactory factory;

    @Override
    public List<PriceToDate> get() throws IOException, ParserConfigurationException, SAXException {
        Document response = factory.request();
        response.getDocumentElement().normalize();
        NodeList list = response.getElementsByTagName("item");

        return getPriceList(list);
    }

    private List<PriceToDate> getPriceList(NodeList list) {
        List<PriceToDate> priceList = new ArrayList<>();

        for (int i = 0; i < list.getLength(); i++) {
            String[] lists = list.item(i).getAttributes().getNamedItem("data").getNodeValue().split("\\|");
            PriceToDate price = new PriceToDate(LocalDate.parse(lists[0]), Integer.parseInt(lists[4]));
            priceList.add(price);
        }

        return priceList;
    }
}
