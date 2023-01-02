from flask import Flask
from flask_socketio import SocketIO
from socketio import Server, WSGIApp
from flask_cors import CORS
from util import Util
from aiohttp import web
import json

app = Flask(__name__)
socketio = Server(async_mode="threading")
app.wsgi_app = WSGIApp(socketio, app.wsgi_app)


@socketio.on('connect')
async def sending_stock_info(info):
    print(info)
    info_list = await json.loads(info)
    price = Util(info_list['ticker'], info_list['market']).now_price()
    while True:
        await socketio.sleep(1)
        await socketio.send(price)


if __name__ == '__main__':
    app.run(port=8080, threaded=True)
