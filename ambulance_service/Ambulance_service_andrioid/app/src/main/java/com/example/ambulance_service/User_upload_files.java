package com.example.ambulance_service;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class User_upload_files extends Activity implements JsonResponse {
	
//	 String rid;
	Button b2;
	EditText e1,e2;
	ImageButton imgbtn;

	public static String labels,pre,des,yt,selectedImagePath;


	String fln, ftype = "", fname, upLoadServerUri;

	public static byte[] byteArray;

	File f = null;
	
	Button btupim;

	private String imagename = "";
	public static Bitmap image;

	final int CAMERA_PIC_REQUEST = 0, GALLERY_CODE = 201;
	public static String encodedImage = "", path = "";
	private Uri mImageCaptureUri;
//	byte[] byteArray = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_upload_files);
		
		try {
		    if(Build.VERSION.SDK_INT>9){
		        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		        StrictMode.setThreadPolicy(policy);
		    }
		} catch (Exception e) { }

		
		btupim=(Button)findViewById(R.id.btupim);
		e1=(EditText)findViewById(R.id.etdes);
		e2=(EditText)findViewById(R.id.etyt);
	
		btupim.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 des=e1.getText().toString();
				 yt=e2.getText().toString();
				 sendAttach();
			}
		});
		
		
		imgbtn=(ImageButton)findViewById(R.id.ibclick);
		
		
		
		imgbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				selectImageOption();
			//	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				//startActivityForResult(i, GALLERY_CODE);
			}
		});
	}
	
	
	
	 private void sendAttach() {
	        // TODO Auto-generated method stub

	        try {
//	            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//	            String uid = sh.getString("uid", "");
	        	
	        	
	        	
//	        	rid=View_waste_disposal_request.report_id;
	        	
	        	
	        	 String q = "http://" +IPSetting.ip+"/api/user_upload_file";
//	            String q = "/user_upload_file/?image="+imagename+"&desc="+des+"&yts="+yt;
//	        	String q = "http://" +IPSetting.ip+"/TeachersHelper/api.php?action=teacher_upload_notes";
//	        	String q= "api.php?action=teacher_upload_notes&sh_id="+Teacher_view_timetable.s_id+"&desc="+des;
//	   Toast.makeText(getApplicationContext(), "Byte Array:"+byteArray.length, Toast.LENGTH_LONG).show();
	   
	            
	            Map<String, byte[]> aa = new HashMap<String, byte[]>();
	            aa.put("image",byteArray);
//	            aa.put("sh_id",Teacher_view_timetable.s_id.getBytes());
				aa.put("am_id",User_view_ambulance.am_id.getBytes());
	            aa.put("yts",yt.getBytes());
	            aa.put("desc",des.getBytes());
	            aa.put("logid",Login.logid.getBytes());
	            aa.put("ftype", ftype.getBytes());
				aa.put("lati", LocationService.lati.getBytes());
				aa.put("logi", LocationService.logi.getBytes());
				System.out.println("----------------------------------------");
	           // Log.d(q,"");
				Log.d("pear_q", q);
	            FileUploadAsync fua = new FileUploadAsync(q);
	            fua.json_response = (JsonResponse) User_upload_files.this;
	            fua.execute(aa);
//	            Toast.makeText(getApplicationContext(), "Byte Array:"+aa, Toast.LENGTH_LONG).show();
//	            String data = fua.getResponse();
//	            stopService(new Intent(getApplicationContext(),Capture.class));
//	            Log.d("response=======", data);
	        } catch (Exception e) {
	            Toast.makeText(getApplicationContext(), "Exception upload : " + e, Toast.LENGTH_SHORT).show();
	        }
	    }


	private void selectImageOption() {

		/*Android 10+ gallery code
        android:requestLegacyExternalStorage="true"*/

		final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

		AlertDialog.Builder builder = new AlertDialog.Builder(User_upload_files.this);
		builder.setTitle("Take Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {

				if (items[item].equals("Capture Photo")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					//intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(intent, CAMERA_PIC_REQUEST);

				} else if (items[item].equals("Choose from Gallery")) {
					Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(i, GALLERY_CODE);

				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	@TargetApi(Build.VERSION_CODES.FROYO)
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

			mImageCaptureUri = data.getData();
			System.out.println("Gallery Image URI : " + mImageCaptureUri);
			//   CropingIMG();

			Uri uri = data.getData();
			Log.d("File Uri", "File Uri: " + uri.toString());
			// Get the path
			//String path = null;
			try {
				path = FileUtils.getPath(this, uri);
				Toast.makeText(getApplicationContext(), "path : " + path, Toast.LENGTH_LONG).show();

				File fl = new File(path);
				int ln = (int) fl.length();

				InputStream inputStream = new FileInputStream(fl);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] b = new byte[ln];
				int bytesRead = 0;

				while ((bytesRead = inputStream.read(b)) != -1) {
					bos.write(b, 0, bytesRead);
				}
				inputStream.close();
				byteArray = bos.toByteArray();

				Bitmap bit = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
				imgbtn.setImageBitmap(bit);

				String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
				encodedImage = str;
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
			}
		} else if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {

			try {
				Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				imgbtn.setImageBitmap(thumbnail);
				byteArray = baos.toByteArray();

				String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
				encodedImage = str;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

		@Override
		public void response(JSONObject jo) {
				
			 try {
		            String status = jo.getString("status");
		            Log.d("result", status);

		            if (status.equalsIgnoreCase("success")) {
		            	Toast.makeText(getApplicationContext(), "Result Found....\n Successfully Updated", Toast.LENGTH_LONG).show();
//		                JSONArray ja = (JSONArray) jo.getJSONArray("data");
//		                labels = ja.getJSONObject(0).getString("label");
//		                pre = ja.getJSONObject(0).getString("precatuions");
		                

		                
		                //startActivity(new Intent(getApplicationContext(),Viewmodeldetails.class));
		                startActivity(new Intent(getApplicationContext(),User_home.class));
		                
		            }
		            else if (status.equalsIgnoreCase("failed")) {
		            
		            	Toast.makeText(getApplicationContext(),"Image Not Found", Toast.LENGTH_LONG).show();
		            	startActivity(new Intent(getApplicationContext(),User_home.class));
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		            Toast.makeText(getApplicationContext(),"Response Exc : " + e.toString(), Toast.LENGTH_LONG).show();
		        }
		}
		
		public void onBackPressed() 
		{
			// TODO Auto-generated method stub
			super.onBackPressed();
			Intent b=new Intent(getApplicationContext(),User_home.class);
			startActivity(b);
		}
		
	    // UPDATED!
	    public String getPaths(Uri uri) {
	        String[] projection = { MediaStore.Video.Media.DATA };
	        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
	        if (cursor != null) {
	            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
	            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
	            int column_index = cursor
	                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
	            cursor.moveToFirst();
	            return cursor.getString(column_index);
	        } else
	            return null;
	    }
	}