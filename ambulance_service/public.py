from flask import*
from database import *

public=Blueprint('public',__name__)

@public.route('/')
def home():
	if not session.get('lid') is None:
		return render_template("home.html")
	else:
		return redirect(url_for('public.login'))

@public.route('/login',methods=['get','post'])
def login():
	session.clear()
	if 'forgotpassword' in request.form:	
		return redirect(url_for('public.login'))

	if 'button' in request.form:
		username=request.form['username']
		password=request.form['password']

		q="select * from login where username='%s' and password='%s'"%(username,password)
		res=select(q)
		if res:
			user=res[0]['user_type']
			if user=='admin':
				return redirect(url_for('admin.admin_home'))
			flash('login success')
		else:
			flash('ivalid username')

	
	return render_template('login.html')

