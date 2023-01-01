import requests
import time
import xml.etree.ElementTree as ET
import pandas as pd
from datetime import datetime
from io import StringIO


class StockInfo:
    def __init__(self, ticker: str):
        self.get_price_url = "https://fchart.stock.naver.com/sise.nhn?timeframe=day&count=6000&requestType=0&symbol={}"
        self.get_dividends_url = "https://query2.finance.yahoo.com/v8/finance/chart/{}.KS"
        self.ticker = ticker
        self.start = f"{time.mktime(datetime.strptime('20210101', '%Y%m%d').timetuple()):.0f}"
        self.end = f"{time.mktime(datetime.strptime('20220101', '%Y%m%d').timetuple()):.0f}"
        self.header = {
            'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36'}

    def prices(self) -> pd.DataFrame:
        url = self.get_price_url.format(self.ticker)
        price_lists = ET.fromstring(requests.get(url).text).find(
            "chartdata").findall("item")
        price_lists_str = '\n'.join([x.attrib.get("data")
                                    for x in price_lists])
        price_df = pd.read_csv(StringIO(price_lists_str), delimiter="|",
                               header=None, dtype={0: str})
        price_df.columns = ['Date', 'Open', 'High', 'Low', 'Close', 'Volume']
        price_df.set_index('Date', inplace=True)
        return price_df

    def dividends(self):
        url = self.get_dividends_url.format(self.ticker)
        data = {
            "period1": self.start,
            "period2": self.end,
            "interval": "1d",
            "includePrePost": False,
            "events": "div,splits"}
        info = requests.get(url, params=data, headers=self.header).json()
        print(info["chart"]["result"][0]["events"]["dividends"])
        # def is_open_day(date: str, list: pd.DataFrame) -> bool:
        #     if date == list.index[-1]:
        #         return True
        #     else:
        #         return False


StockInfo("005930").dividends()
