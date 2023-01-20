package com.finescore.moneycookie.services.generator;

import com.fasterxml.jackson.databind.JsonNode;
import com.finescore.moneycookie.services.factory.ConvertDateRequestFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;

public class ConvertDateGenerator implements Generator<LocalDate> {
    private final ConvertDateRequestFactory factory;

    public ConvertDateGenerator(int year, int month, int day) {
        this.factory = new ConvertDateRequestFactory(year, month, day);
    }

    @Override
    public LocalDate get() throws IOException, ParserConfigurationException, SAXException {
        JsonNode response = factory.request();
        int solarYear = Integer.parseInt(response.get("SOLC_YYYY").asText());
        int solarMonth = Integer.parseInt(response.get("SOLC_MM").asText());
        int solarDay = Integer.parseInt(response.get("SOLC_DD").asText());

        return LocalDate.of(solarYear, solarMonth, solarDay);
    }
}
