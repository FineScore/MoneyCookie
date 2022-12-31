import asyncio
import websockets
import datetime
import schedule
from stock_krx import price
from divi_inquiry import dividend
import time
import json


async def send_stock(websocket):
    now_date = datetime.datetime.now().strftime("%Y%m%d")
    ticker = await websocket.recv()
    while True:
        now_price = price(ticker, now_date)
        message = json.dumps({
            "code": 1,
            "ticker": ticker,
            "price": int(now_price[0])
        })
        await websocket.send(message)
        await asyncio.sleep(1)


async def main():
    async with websockets.serve(send_stock, "localhost", 8081):
        await asyncio.Future()

if __name__ == "__main__":
    asyncio.run(main())
