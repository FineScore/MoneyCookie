from pykrx import stock

def price(ticker, date):
    return stock.get_market_ohlcv(date, date, ticker)['종가'].values
