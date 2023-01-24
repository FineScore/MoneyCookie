package com.finescore.moneycookie.network.factory;

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
    public Document request(ItemInfo info) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;

        try {
            builder = builderFactory.newDocumentBuilder();
            return builder.parse(setURL(info));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    String setURL(ItemInfo info) {
        return String.format(PRICE_URL, info.getTicker());
    }
}
