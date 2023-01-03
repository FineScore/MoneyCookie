from read import Reader
import json


class Util(Reader):
    def __init__(self, ticker: str, market: str, start=None):
        super().__init__(ticker, market, start)

    def now_price(self) -> str:
        data = {
            "ticker": self.ticker,
            "price": str(self.prices().tail(1)['Close'].values[0])
        }
        return json.dumps(data)

    def from_start_price(self) -> str:
        return self.prices()[self.start:]['Close'].to_json()

    def expect_dividends(self) -> str:
        return self.dividends().to_json()


# print(Util("317530", "KQ").expect_dividends())
