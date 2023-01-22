package com.finescore.moneycookie.services.factory;

public interface RequestURLContants {
    String PRICE_URL = "https://fchart.stock.naver.com/sise.nhn?timeframe=day&count=6000&requestType=0&symbol=%s";
    String ALL_ITEMS_URL = "http://data.krx.co.kr/comm/bldAttendant/getJsonData.cmd?bld={bld}&mktsel={mktsel}&searchText={searchText}";
    String DIVIDEND_URL = "https://query2.finance.yahoo.com/v8/finance/chart/%s.%s?period1={period1}&period2={period2}&interval={interval}&includePrePost={includePrePost}&events={events}";
    String HOLIDAY_URL = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?solYear={year}&serviceKey=%2FS3lvmOXDstcmEQBFyBCOuEAbFWuWx68Rm5XUeF5iWVtNNGWgdzArkw6Y7vu1miYgXHvN52i8LK8PyCuIEyuOA%3D%3D&_type=json&numOfRows=20";
}
