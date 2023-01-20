package com.finescore.moneycookie.services.factory;

public interface RequestURLContants {
    String PRICE_URL = "https://fchart.stock.naver.com/sise.nhn?timeframe=day&count=6000&requestType=0&symbol=%s";
    String ALL_ITEMS_URL = "http://data.krx.co.kr/comm/bldAttendant/getJsonData.cmd?bld={bld}&mktsel={mktsel}&searchText={searchText}";
    String DIVIDEND_URL = "https://query2.finance.yahoo.com/v8/finance/chart/%s.%s?period1={period1}&period2={period2}&interval={interval}&includePrePost={includePrePost}&events={events}";
    String LUNA_TO_SOLAR_URL = "https://astro.kasi.re.kr/life/lunc?yyyy={year}&mm={month}&dd={day}";
}
