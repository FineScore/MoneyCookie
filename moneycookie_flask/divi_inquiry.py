import yfinance


def dividend(ticker, year):
    stock = yfinance.Ticker(f"{ticker}.KS")
    return stock.dividends[stock.dividends.index.year == year]
