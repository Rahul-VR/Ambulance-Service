from flask import *
from database import *

admin=Blueprint('admin',__name__)

@admin.route('/admin_home')
def admin_home():
	
	
	return render_template('admin_home.html')

@admin.route('/admin_manage_ambulance', methods=['get','post'])
def admin_manage_ambulance():
	data={}
	if 'submit' in request.form:
		driver=request.form['driver']
		place=request.form['place']
		phone=request.form['phone']
		email=request.form['email']
		lat=request.form['lat']
		lon=request.form['lon']
		username=request.form['username']
		password=request.form['password']

		q="INSERT INTO `login` VALUES(NULL,'%s','%s','ambulance')"%(username,password)
		res=insert(q)

		q="INSERT INTO `ambulance` VALUES(NULL,'%s','%s','%s','%s','%s','%s','%s')"%(res,driver,place,phone,email,lat,lon)
		insert(q)

	if 'action' in request.args:
		action=request.args['action']
		amid=request.args['amid']
	else:
		action=None

	if action=='delete':

		q="DELETE FROM `ambulance` WHERE `ambulance_id`='%s'"%(amid)
		print(q)
		delete(q)

	if action=="update":
		q="select * from ambulance where ambulance_id='%s'"%(amid)
		print(q)
		res1=select(q)
		data['up']=res1

	if 'update' in request.form:
		driver=request.form['driver']
		phone=request.form['phone']
		email=request.form['email']
		place=request.form['place']
		lat=request.form['lat']
		lon=request.form['lon']

		q="UPDATE `ambulance` SET `driver`='%s' , `phone` ='%s' , `email` ='%s' ,`place`='%s',`latitude`='%s' , `longitude`='%s' where ambulance_id='%s'"%(driver,phone,email,place,lat,lon,amid)
		update(q)

		return redirect(url_for('admin.admin_manage_ambulance'))


	q="SELECT * FROM `ambulance`"
	res=select(q)
	data['view']=res
	return render_template('admin_manage_ambulance.html',data=data)

@admin.route('/admin_view_patient',methods=['get','post'])
def admin_view_patient():
	data={}

	q="SELECT * FROM `login` inner join user using(login_id)"
	print(q)
	res=select(q)
	data['view']=res

	if 'action' in request.args:
		action=request.args['action']
		lid=request.args['lid']
	else:
		action=None

	if action=='activate':
		q="UPDATE `login` SET `user_type`='user' WHERE login_id='%s'"%(lid)
		print(q)
		update(q)
		flash(' user_type add successfully')
		return redirect(url_for('admin.admin_view_patient'))


	if action=='deactivate':
		q="UPDATE `login` SET `user_type`='Rejected' WHERE login_id='%s'"%(lid)
		print(q)
		update(q)
		flash(' user_type add successfully')
		return redirect(url_for('admin.admin_view_patient'))

	return render_template('admin_view_patient.html',data=data)

@admin.route('/admin_view_request',methods=['get','post'])
def admin_view_request():
	data={}

	q="SELECT * FROM `request` INNER JOIN `user` ON `request`.`patient_id` = `user`.`user_id`"
	res=select(q)
	data['view']=res
	
	return render_template('admin_view_request.html',data=data)

# @admin.route('/admin_view_payment',methods=['get','post'])
# def admin_view_payment():
# 	data={}
# 	uid=request.args['user_id']

# 	q="select * from payment where user_id='%s'"%(uid)
# 	res=select(q)
# 	data['view']=res
	
# 	return render_template('admin_view_payment.html',data=data)


@admin.route('/admin_view_complaints',methods=['get','post'])
def admin_view_complaints():
	data={}


	q="select * from complaint inner join user using(user_id)"
	res=select(q)
	data['view']=res

	j=0
	for i in range(1,len(res)+1):
		print('submit'+str(i))
		if 'submit'+str(i) in request.form:
			print('hai'+str(i))
			reply=request.form['reply'+str(i)]
			q="UPDATE `complaint` SET `reply`='%s' , `date`=CURDATE() WHERE `complaint_id`='%s'"%(reply,res[j]['complaint_id'])
			print(q)
			update(q)
			return redirect(url_for('admin.admin_view_complaints'))
		j=j+1



	return render_template('admin_view_complaints.html',data=data)

@admin.route('/admin_view_review',methods=['get','post'])
def admin_view_review():
	data={}

	q="select * from review"
	res=select(q)
	data['view']=res
	return render_template('admin_view_review.html',data=data)

@admin.route('/admin_view_payment',methods=['get','post'])
def admin_view_payment():
	data={}
	uid=request.args['uid']
	q="SELECT * FROM `payment` INNER JOIN `user` ON `payment`.`user_id`=`user`.`user_id` where payment.user_id='%s'"%(uid)
	print(q)
	res=select(q)
	data['view']=res

	return render_template('admin_view_payment.html',data=data)

