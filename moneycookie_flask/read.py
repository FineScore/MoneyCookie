import requests
import time
import xml.etree.ElementTree as ET
import pandas as pd
from datetime import datetime
from io import StringIO


class Reader:
    def __init__(self, ticker='', market='', start=None):
        self.get_price_url = "https://fchart.stock.naver.com/sise.nhn?timeframe=day&count=6000&requestType=0&symbol={}"
        self.get_dividends_url = "https://query2.finance.yahoo.com/v8/finance/chart/{}.{}"
        self.get_all_ticker = "http://data.krx.co.kr/comm/bldAttendant/getJsonData.cmd"
        self.ticker = ticker
        self.market = market
        self.start = start
        self.year_start = f"{time.mktime(datetime.strptime('20210101', '%Y%m%d').timetuple()):.0f}"
        self.year_end = f"{time.mktime(datetime.strptime('20220101', '%Y%m%d').timetuple()):.0f}"
        self.header = {
            'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36'}

    def prices(self) -> pd.DataFrame:
        url = self.get_price_url.format(self.ticker)
        lists = ET.fromstring(requests.get(url).text).find(
            "chartdata").findall("item")
        lists_str = '\n'.join([x.attrib.get("data")
                               for x in lists])
        df = pd.read_csv(StringIO(lists_str), delimiter="|",
                         header=None, dtype={0: str})
        df.columns = ['Date', 'Open', 'High', 'Low', 'Close', 'Volume']
        df.set_index('Date', inplace=True)
        return df

    def dividends(self) -> pd.DataFrame:
        url = self.get_dividends_url.format(self.ticker, self.market)
        data = {
            "period1": self.year_start,
            "period2": self.year_end,
            "interval": "1d",
            "includePrePost": False,
            "events": "div,splits"
        }
        info = requests.get(url, params=data, headers=self.header).json()
        try:
            all_dict = info["chart"]["result"][0]["events"]["dividends"]
        except:
            return pd.DataFrame()
        lists = [all_dict[key] for key in all_dict]
        for x in lists:
            x['amount'] = int(x['amount'])
            x['date'] = datetime.fromtimestamp(x['date']).strftime('%Y%m%d')
        df = pd.DataFrame(lists)
        df.columns = ['Amount', 'Date']
        # df.set_index('Date', inplace=True)
        return df

    def all_listed_items(self) -> pd.DataFrame:
        url = self.get_all_ticker
        data = {
            'bld': "dbms/comm/finder/finder_stkisu",
            'mktsel': 'ALL',
            'searchText': '',
        }
        lists = requests.post(url, data=data, headers=self.header).json()
        df = pd.DataFrame(lists['block1'])
        df = df[['short_code', 'codeName', 'marketEngName']]
        df.columns = ['Ticker', 'Name', 'Market']
        return df
