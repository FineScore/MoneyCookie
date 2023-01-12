package com.finescore.moneycookie.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finescore.moneycookie.models.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class StockFactory {
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public ArrayList<PriceToDate> getTotalPrice(String ticker) throws ParserConfigurationException, IOException, SAXException {
        String priceUrl = "https://fchart.stock.naver.com/sise.nhn?timeframe=day&count=6000&requestType=0&symbol=%s";
        String url = String.format(priceUrl, ticker);
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        ArrayList<PriceToDate> priceList = new ArrayList<>();

        Document document = builder.parse(url);
        document.getDocumentElement().normalize();
        NodeList list = document.getElementsByTagName("item");

        for (int i = 0; i < list.getLength(); i++) {
            String[] lists = list.item(i).getAttributes().getNamedItem("data").getNodeValue().split("\\|");
            PriceToDate price = new PriceToDate(lists[0], Integer.parseInt(lists[4]));
            priceList.add(price);
        }

        return priceList;
    }

    public PriceNow getNowPrice(String ticker) throws ParserConfigurationException, IOException, SAXException {
        log.info("실행 : {}", ticker);
        ArrayList<PriceToDate> totalPrice = getTotalPrice(ticker);

        return new PriceNow(ticker, totalPrice.get(totalPrice.size() - 1).getPrice());
    }

    public PriceToPeriod getPeriodPrice(String ticker, String date) throws ParserConfigurationException, IOException, SAXException {
        ArrayList<PriceToDate> totalPrice = getTotalPrice(ticker);
        ArrayList<PriceToDate> dateList = new ArrayList<>();

        for (int i = totalPrice.size() - 1; i <= 0; i--) {
            if (date.equals(totalPrice.get(i).getDate())) {
                for (int j = i; j < totalPrice.size(); j++) {
                    dateList.add(totalPrice.get(j));
                }

                break;
            }
        }

        return new PriceToPeriod(ticker, dateList);
    }

    public ArrayList<Dividend> getDividends(String ticker, String market) throws JsonProcessingException {
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36";
        String dividendUrl = "https://query2.finance.yahoo.com/v8/finance/chart/%s.%s?period1={period1}&period2={period2}&interval={interval}&includePrePost={includePrePost}&events={events}";
        LocalDate startDate = LocalDate.of(
                LocalDate.now().getYear() - 1,
                LocalDate.now().getMonth().plus(1),
                1
        );
        LocalDate endDate = LocalDate.of(
                LocalDate.now().getYear(),
                LocalDate.now().getMonth(),
                1
        );
        Instant startInstant = startDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant endInstant = endDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        String url = String.format(dividendUrl, ticker, market);
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", userAgent);
        Map<String, String> params = new HashMap<>();
        params.put("period1", String.valueOf(startInstant.toEpochMilli() / 1000));
        params.put("period2", String.valueOf(endInstant.toEpochMilli() / 1000));
        params.put("interval", "1d");
        params.put("includePrePost", "false");
        params.put("events", "div,splits");
        HttpEntity<String> request = new HttpEntity<>(headers);
        String response = restTemplate.exchange(url, HttpMethod.GET, request, String.class, params).getBody();
        JsonNode root = objectMapper.readTree(response);
        JsonNode dividends = root.get("chart")
                .get("result")
                .get(0)
                .get("events")
                .get("dividends");
        ArrayList<Dividend> list = new ArrayList<>();

        for (JsonNode objectNode : dividends) {
            LocalDate localDate =
                    Instant.ofEpochMilli(objectNode.get("date").asLong() * 1000)
                            .atZone(ZoneOffset.UTC)
                            .toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String date = localDate.format(formatter);
            Integer price = objectNode.get("amount").asInt();
            System.out.println(date);
            System.out.println(price);
            list.add(new Dividend(date, price));
        }

        return list;
    }

    public ArrayList<StockItem> getAllItems() throws JsonProcessingException {
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36";
        String allItemsUrl = "http://data.krx.co.kr/comm/bldAttendant/getJsonData.cmd?bld={bld}&mktsel={mktsel}&searchText={searchText}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", userAgent);
        Map<String, String> params = new HashMap<>();
        params.put("bld", "dbms/comm/finder/finder_stkisu");
        params.put("mktsel", "ALL");
        params.put("searchText", "");

        HttpEntity<String> request = new HttpEntity<>(headers);
        String response = restTemplate.exchange(allItemsUrl, HttpMethod.POST, request, String.class, params).getBody();
        JsonNode root = objectMapper.readTree(response);
        JsonNode allItems = root.get("block1");
        ArrayList<StockItem> list = new ArrayList<>();

        for (JsonNode item : allItems) {
            String shortCode = item.get("short_code").asText();
            String name = item.get("codeName").asText();
            String market = item.get("marketEngName").asText();
            list.add(new StockItem(shortCode, name, market));
        }

        return list;
    }
}
