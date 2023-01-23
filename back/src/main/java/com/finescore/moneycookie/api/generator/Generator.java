package com.finescore.moneycookie.api.generator;

import com.finescore.moneycookie.models.PriceToTicker;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;

public interface Generator<T> {
   default PriceToTicker get(T t) throws ParserConfigurationException, IOException, SAXException {
      return null;
   };

   default T get() throws IOException, ParserConfigurationException, SAXException {
      return null;
   }
}
