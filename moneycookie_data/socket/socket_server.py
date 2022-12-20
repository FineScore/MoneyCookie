import asyncio
import websockets
import datetime
import schedule
from stock.stock_inquiry import price
from stock.divi_inquiry import dividend


async def stock(websocket):
    now_date = datetime.datetime.now().strftime("%Y%m%d")
    ticker = await websocket.recv()
    now_price = price(ticker, now_date)

    await websocket.send(now_price)


async def main():
    async with websockets.serve(stock, "localhost", 8080):
        await asyncio.Future()

if __name__ == "__main__":
    asyncio.run(main())
