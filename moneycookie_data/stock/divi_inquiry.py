import yfinance

stock = yfinance.Ticker("005930.KS")

print(stock.dividends[stock.dividends.index.year == 2021])
