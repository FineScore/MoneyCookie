package com.finescore.moneycookie.services.factory;

import com.fasterxml.jackson.databind.JsonNode;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface RequestFactory<R> {
    default JsonNode request() throws IOException {
        return null;
    }

    default <T> T request(R r) throws ParserConfigurationException, IOException, SAXException {
        return null;
    }
}
