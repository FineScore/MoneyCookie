import json
from flask import Flask
from util import Util
import websockets
import asyncio

app = Flask(__name__)


async def send(websocket):
    info_list = await websocket.recv()
    info_list = json.loads(info_list)
    while True:
        price = Util(info_list['ticker'], info_list['market']).now_price()
        print(price)
        await websocket.send(price)
        await asyncio.sleep(1)


async def main():
    async with websockets.serve(send, "localhost", 8081):
        await asyncio.Future()

if __name__ == '__main__':
    asyncio.run(main())
