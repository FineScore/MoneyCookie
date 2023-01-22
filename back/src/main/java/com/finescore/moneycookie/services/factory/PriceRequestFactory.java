package com.finescore.moneycookie.services.factory;

import com.finescore.moneycookie.models.ItemInfo;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Service
public class PriceRequestFactory extends XMLRequestFactory<ItemInfo> implements RequestURLContants {

    @Override
    public Document request(ItemInfo info) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();

        return builder.parse(setURL(info));
    }

    @Override
    String setURL(ItemInfo info) {
        return String.format(PRICE_URL, info.getTicker());
    }
}
