from flask import *
from public import public
from admin import admin
from api import api


app=Flask(__name__)

app.secret_key="abnn"
app.register_blueprint(public)
app.register_blueprint(admin,url_prifix='/admin')
app.register_blueprint(api,url_prefix='/api')


# app.run( port=5010,host="192.168.45.106")
app.run(debug=True, port=5047,host="0.0.0.0")