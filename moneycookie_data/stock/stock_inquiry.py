from pykrx import stock
import schedule
import time


def closing_price():
    return stock.get_market_ohlcv('20221219',
                                  '20221219', '005930')['종가'].values


# schedule.every(5).seconds.do(execute)

# while True:
#     schedule.run_pending()
#     time.sleep(5)
