package com.finescore.moneycookie.services.factory;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface RequestFactory<T> {
    T request() throws ParserConfigurationException, IOException, SAXException;
}
