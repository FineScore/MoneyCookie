package com.finescore.moneycookie.services.generator;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface Generator<T> {
    T get() throws IOException, ParserConfigurationException, SAXException;
}
