from flask import *
from database import *
import uuid

api=Blueprint('api',__name__)


@api.route("/login")
def login():
	data={}

	username=request.args['username']
	password=request.args['password']


	print(username,password)
	q="select * from login where username='%s' and password='%s'"%(username,password)
	res=select(q)
	if res:
		
		data['status']='success'
		data['data']=res
	else:
		data['status']='failed'

	return str(data)


@api.route("/User_registration")
def User_registration():
	data={}

	firstname=request.args['firstname']
	lastname=request.args['lastname']
	phone=request.args['phone']
	email=request.args['email']
	place=request.args['place']
	username=request.args['username']
	password=request.args['password']

	q="insert into login values(null,'%s','%s','pending')"%(username,password)
	print(q)
	id=insert(q)

	q="insert into user values(null,'%s','%s','%s','%s','%s','%s')"%(id,firstname,lastname,place,phone,email)
	print(q)
	insert(q)
	data['status']='success'
	return str(data)

@api.route('/User_view_request')
def User_view_request():
	data={}
	log_id=request.args['log_id']
	# am_id=request.args['am_id']
	
	q="SELECT  * FROM `request`  inner join ambulance ON `request`.`ambulace_id`=`ambulance`.`ambulance_id` where patient_id=(select user_id from user where login_id='%s')  GROUP BY `ambulace_id`"%(log_id)
	print(q)
	res=select(q)
	if res:
		data['status']='success'
		data['data']=res
	else:
		data['status']='failed'
	data['method']='User_view_request'
	return str(data)



@api.route('/Review')
def Review():
    data={}
    log_id=request.args['log_id']
    am_id=request.args['am_id']
    rating=request.args['rating']
    review=request.args['review']
    q="SELECT * FROM `review` WHERE `ambulance_id`='%s' AND `patient_id`=(SELECT `user_id` FROM `user` WHERE login_id='%s')"%(am_id,log_id)
    res=select(q)
    if res:
    	q="UPDATE `review` SET `rating`='%s' , `review`='%s' ,`date`=CURDATE() WHERE `ambulance_id`='%s'"%(rating,review,am_id)
    	update(q)
    	data['status'] = 'success'
    else:
    	q="INSERT INTO `review`  VALUES(NULL,(SELECT `user_id` FROM `user` WHERE `login_id`='%s'),'%s','%s','%s',CURDATE())"%(log_id,am_id,rating,review)
    	id=insert(q)
    	if id>0:
    		data['status'] = 'success'
    	else:
    		data['status'] = 'failed'
    data['method'] = 'Review'
    return str(data)

@api.route("/viewrating")
def viewrating():
    data={}
    ambulace_id=request.args['am_id']
    logid=request.args['log_id']
    q="select * from review where patient_id=(select user_id from user where login_id='%s') and ambulance_id='%s'"%(logid,ambulace_id)
    print(q)
    val=select(q)
    if val:
        data['status'] = 'success'
        data['data'] = val[0]['rating']
        data['data1'] = val[0]['review']
    else:
    	data['status'] = 'failed'
    data['method']="viewrating" 
    return str(data)



@api.route('/complaint')
def complaint():
	data={}
	log_id=request.args['log_id']
	q="select * from complaint where user_id=(select user_id from user where login_id='%s')"%(log_id)
	print(q)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="view"
	return str(data)




@api.route('/addcomplaint')
def addcomplaint():
	data={}
	log_id=request.args['log_id']
	complaint=request.args['Complaint']
	q="insert into complaint values(null,(select user_id from user where login_id='%s'),'%s','pending',curdate())"%(log_id,complaint)
	print(q)
	res=insert(q)
	if res:
		data['status']="success"
		data['data']=res 
	else:
		data['status']="failed"
	data['method']="view_complaint"
	return str(data)



@api.route('/payment')
def payment():
	data={}
	# work=request.args['Work']
	amount=request.args['Amount']
	log_id=request.args['log_id']
	req_id=request.args['req_id']
	
	q="insert into payment values(null,'%s',(select user_id from user where login_id='%s'),'%s',curdate())"%(req_id,log_id,amount)
	print(q)
	res1=insert(q)

	# q="update request set status='accept' where request_id='%s'"%(req_id)
	# print(q)
	# res1=update(q)
	if res1:
		data['status']="success"
		data['data']=res1
	else:
		data['status']="failed"
	data['method']="payment"
	return str(data)

@api.route('/User_view_ambulance')
def User_view_ambulance():

	data={}
	q=" SELECT * FROM `ambulance`"
	print("ssssssssssssssssssssssssssssssssss",q)
	res=select(q)
	print(res)

	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="User_view_ambulance"
	return str(data)



# @api.route('/upload_image',methods=['get','post'])
# def upload_image():

# 	data={}
# 	image=request.files['image']
# 	path="static/uploads/"+str(uuid.uuid4())+image.filename
# 	image.save(path)
# 	logid=request.form['logid']
# 	am_id=request.args['am_id']
# 	lati=request.form['lati']
# 	logi=request.form['logi']

# 	q=  "INSERT into `request` values(null,'%s',`patient_id`=(SELECT `user_id` FROM `user` where login_id='%s'),'%s',curdate(),'pending')"% (am_id,logid,PATH,lati,logi)

# 	print(q)
# 	id=insert(q)
# 	# q= "INSERT INTO `images` values(null,'%s','%s',curdate())"% (path,id)
# 	# print(q)
# 	# id=insert(q)

# 	if id>0:
# 		data['status'] = 'success'
# 	else:
# 		data['status'] = 'failed'
# 	data['method'] = 'upload_image'
# 	return str(data)

@api.route('/user_upload_file',methods=['get','post'])
def user_upload_file():

	data={}
	am_id=request.form['am_id']
	yts=request.form['yts']
	desc=request.form['desc']
	logid=request.form['logid']
	image=request.files['image']
	path='static/uploads/'+str(uuid.uuid4())+ ".jpg"
	image.save(path)
	lati=request.form['lati']
	logi=request.form['logi']

		


	# q="INSERT INTO `uploads` VALUES(NULL,'%s','%s','%s','%s','%s')"%(trv_id,logid,path,yts,desc)
	q="INSERT INTO `request` VALUES(NULL,'%s',(SELECT `user_id` FROM `user` WHERE login_id='%s'),'%s','%s','%s','%s','%s',CURDATE(),'pending')"% (am_id,logid,path,yts,desc,lati,logi)
	print(q)
	res=insert(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="User_view_ambulance"
	return str(data)
	# if id:
	# 	data['status'] = 'success'
	# 	data['method'] = 'user_upload_file'
	# else:
	# 	data['status'] = 'failed'
	# 	data['method'] = 'user_upload_file'
	# return demjson.encode(data)







###############################Ambulace##############################################################################

#############################################################################################


@api.route('/Ambulance_view_request')
def Ambulance_view_request():
	data={}
	log_id=request.args['log_id']
	
	# lati=request.args['latitude']
	# logi=request.args['longitude']
	# q = "SELECT *,(3959 * ACOS ( COS ( RADIANS('%s') ) * COS( RADIANS( latitude) ) * COS( RADIANS( longitude ) - RADIANS('%s') ) + SIN ( RADIANS('%s') ) * SIN( RADIANS(latitude ) ))) AS user_distance  FROM colleges inner join ranking using(college_id)  HAVING user_distance<31.068 ORDER BY rank desc" % (lati,logi,lati)
	# q="SELECT *,(3959 * ACOS ( COS ( RADIANS('%s') ) * COS( RADIANS( latitude) ) * COS( RADIANS( longitude ) - RADIANS('%s') ) + SIN ( RADIANS('%s') ) * SIN( RADIANS(latitude ) ))) AS user_distance  FROM `request` INNER JOIN `user` ON `user`.`user_id`=`request`.`patient_id`  HAVING user_distance<31.068 " % (lati,logi,lati)
	

	q="SELECT  * FROM `request` INNER JOIN `user` ON `request`.`patient_id`=`user`.`user_id` WHERE ambulace_id=(select ambulace_id from ambulance where login_id='%s')"%(log_id)
	print(q)
	res=select(q)
	if res:
		data['status']='success'
		data['data']=res
	else:
		data['status']='failed'
	data['method']='Ambulance_view_request'
	return str(data)



@api.route('/accept',methods=['get','post'])
def accept():

	data={}
	req_id=request.args['req_id']

	q= "update request set status='approve' where request_id='%s'"%(req_id) 
	print(q)
	update(q)
	data['status'] = 'success'
	
	data['method'] = 'approve'
	return str(data)


@api.route('/reject')
def reject():
	req_id=request.args['req_id']

	data={}

	q= "update request set status='reject' where request_id='%s'"%(req_id)
	print(q)
	update(q)
	data['status'] = 'success'
	data['method'] = 'reject'
	return str(data)

@api.route("/Ambulance_view_review")
def Ambulance_view_review():
    data={}
   
    logid=request.args['log_id']
    q="select * from review INNER JOIN `user` ON `review`.`patient_id`=`user`.`user_id` where ambulance_id=(select ambulance_id from ambulance where login_id='%s')"%(logid)
    print(q)
    val=select(q)
    if val:
    	data['status']="success"
    	data['data']=val
    else:
    	data['status']="failed"
    data['method']="Ambulance_view_review" 
    return str(data)

@api.route('/Ambulance_view_payment')
def Ambulance_view_payment():
	data={}
	req_id=request.args['req_id']

	q="SELECT * FROM `payment` inner join request using(request_id) WHERE `request_id`='%s'"%(req_id)
	print(q)
	val=select(q)
	print(val)

	if val:
		data['status']="success"
		data['data']=val
	else:
		data['status']="failed"
	data['method']="Ambulance_view_payment" 
	return str(data)

@api.route('/viewprofile')
def viewprofile():
	data={}
	log_id=request.args['lid']
	q=" SELECT * FROM `ambulance` WHERE login_id='%s'"%(log_id)
	print(q)
	val=select(q)
	print(val)

	if val:
		data['status']="success"
		data['data']=val
	else:
		data['status']="failed"
	data['method']="viewprofile" 
	return str(data)

@api.route('/Ambulace_update_profile')
def Ambulace_update_profile():
	data={}
	log_id=request.args['log_id']
	driver=request.args['driver']
	place=request.args['place']
	phone=request.args['phone']
	email=request.args['email']
	saddr=request.args['saddr']
	# lon=request.args['daddr']
	q="UPDATE `ambulance` SET `driver`='%s' , `place`='%s' ,`phone`='%s' , `email`='%s' , latitude='%s' , longitude='%s'  WHERE `ambulance_id`=(select ambulance_id from ambulance where login_id='%s')"%(driver,place,phone,email,saddr,saddr,log_id)
	print(q)
	val=update(q)
	print(q)

	if val:
		data['status']="success"
		data['data']=val
	else:
		data['status']="failed"
	data['method']="Ambulace_update_profile" 
	return str(data)


@api.route('/Ambulance_view_image')
def Ambulance_view_image():
	data={}
	log_id=request.args['log_id']
	q=" SELECT * FROM `request` WHERE `ambulace_id`=(select ambulance_id from ambulance where login_id='%s')"%(log_id)
	print(q)
	val=select(q)
	print(val)

	if val:
		data['status']="success"
		data['data']=val
	else:
		data['status']="failed"
	data['method']="Ambulance_view_image" 
	return str(data)






