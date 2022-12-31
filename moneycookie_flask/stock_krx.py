import requests
import json
import pandas as pd


class KrxStockInfo:
    def get_stock_fullcode(ticker):
        url = "http://data.krx.co.kr/comm/bldAttendant/getJsonData.cmd"
        data = {
            'mktsel': 'ALL',
            'searchText': '',
            'bld': 'dbms/comm/finder/finder_stkisu',
        }
        r = requests.post(url, data)
        j = json.loads(r.text)
        df = pd.json_normalize(j['block1']).set_index('short_code')
        full_code = df.loc[ticker]['full_code']
        return full_code

    def get_stock_open(full_code):
        url = "http://data.krx.co.kr/comm/bldAttendant/getJsonData.cmd"
        data = {
            'bld': 'dbms/MDC/STAT/issue/MDCSTAT23902',
            'isuCd': full_code,
            'isuCd2': '',
            'strtDd': "20221228",
            'endDd': "20221228",
            'share': '1',
            'money': '1',
            'csvxls_isNo': 'false',
        }
        r = requests.post(url, data)
        j = json.loads(r.text)
        print(j)


KrxStockInfo.get_stock_open("KR7005930003")
