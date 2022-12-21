import asyncio
import websockets
import datetime
import schedule
from stock_inquiry import price
from divi_inquiry import dividend


async def stock(websocket):
    now_date = datetime.datetime.now().strftime("%Y%m%d")
    ticker = await websocket.recv()
    now_price = price(ticker, now_date)

    await websocket.send(str(now_price[0]))


async def main():
    async with websockets.serve(stock, "localhost", 8081):
        await asyncio.Future()

if __name__ == "__main__":
    asyncio.run(main())
