package com.example.ambulance_service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@SuppressLint("NewApi") 
 public class CameraService extends Service implements TextToSpeech.OnInitListener, JsonResponse
 {
    TextToSpeech t1;

    //Camera variables
    //a surface holder
    private SurfaceHolder sHolder;
    //a variable to control the camera
    private Camera mCamera;
    //the camera parameters
    private Parameters parameters;
    
    SharedPreferences sh1;

    String encodedImage = "";
    public static byte[] imdData = null;

    Handler hd;
    boolean killMe = true;

    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate() {
        super.onCreate();
        t1 = new TextToSpeech(getApplicationContext(), CameraService.this);
        sh1=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);

        hd = new Handler();
        hd.post(r);

        mCamera = Camera.open();
    }

    @SuppressLint("NewApi") public Runnable r = new Runnable() {
        @Override
        public void run() {

//            Toast.makeText(getApplicationContext(), "Start", Toast.LENGTH_LONG).show();

        	if (killMe) {
                SurfaceView sv = new SurfaceView(getApplicationContext());

                try {
                    mCamera.setPreviewDisplay(sv.getHolder());
                    parameters = mCamera.getParameters();

                    SurfaceTexture st = new SurfaceTexture(MODE_PRIVATE);
                    mCamera.setPreviewTexture(st);

                    //set camera parameters
                    mCamera.setParameters(parameters);
                    mCamera.startPreview();
                    mCamera.takePicture(null, null, mCall);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                //Get a surface
                sHolder = sv.getHolder();
                //tells Android that this surface will have its data constantly replaced
                sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        	}

            hd.postDelayed(r, 60000);
        }
    };

    @SuppressWarnings("deprecation")
	Camera.PictureCallback mCall = new Camera.PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
            //decode the data obtained by the camera into a Bitmap

            FileOutputStream outStream = null;
            try {
                imdData = data;
                encodedImage = Base64.encodeToString(imdData, Base64.DEFAULT);

//                SimpleDateFormat sdf = new SimpleDateFormat("hh_mm_ss");
//                String dateVal = sdf.format(new Date());
//                outStream = new FileOutputStream("/sdcard/" + dateVal + ".jpg");
//                outStream.write(imdData);
//                outStream.close();

                Toast.makeText(getApplicationContext(), "Captured", Toast.LENGTH_LONG).show();
                speakResult("Captured");
                
               
              sendAttach();
            } catch (Exception e) {
                Log.d("CAMERA", e.getMessage());
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        killMe = false;
        hd.removeCallbacks(r);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    private void sendAttach() {

        try {
//            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//            String uid = sh.getString("uid", "");

//            String q = "/citizen_upload_image/?logi="+LocationService.logi+"&lati="+LocationService.lati+"&imei="+IMEI_Number_Holder+"&image="+imagename;
        	String q = "http://" +IPSetting.ip+"/api/upload_image";
		    Map<String,byte[]> aa=new HashMap<String,byte[]> ();
		    aa.put("image", imdData);
		    aa.put("lo_gid",  Login.logid.getBytes());
            aa.put("am_id", User_view_ambulance.am_id.getBytes());
		    aa.put("lati", LocationService.lati.getBytes());
		    aa.put("logi", LocationService.logi.getBytes());
		    Log.d("pear_q", q);
		    FileUploadAsync fua = new FileUploadAsync(q);
		    fua.json_response = (JsonResponse) CameraService.this;
		    fua.execute(aa);
		    Toast.makeText(getApplicationContext(), "camera..."+q, Toast.LENGTH_LONG).show();
		    Toast.makeText(getApplicationContext(), "Byte Array:"+aa, Toast.LENGTH_LONG).show();
		//    String data = fua.getResponse();
		//    stopService(new Intent(getApplicationContext(),Capture.class));
		//    Log.d("response=======", data);
		} catch (Exception e) {
		    Toast.makeText(getApplicationContext(), "Exception upload : " + e, Toast.LENGTH_SHORT).show();
		}
    
    }

    @Override
    public void onInit(int status)
    {
        // TODO Auto-generated method stub
        if (status == TextToSpeech.SUCCESS) {
            int result = t1.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("error", "This Language is not supported");
            } else {
                speakResult("Camera...................Open");
            }
        } else {
            Log.e("error", "Initilization Failed!");
        }
    }
    
    void speakResult(String voice) 
    {
        t1.speak(voice, TextToSpeech.QUEUE_FLUSH, null);
    }

	@Override
	public void response(JSONObject jo) {
		killMe = false;
		hd.removeCallbacks(r);
        
	 	Toast.makeText(getApplicationContext(),"success", Toast.LENGTH_SHORT).show();
//		String emno=sh1.getString("emno","");
//		Intent callIntent = new Intent(Intent.ACTION_CALL);
//		callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		callIntent.setData(Uri.parse("tel:" + emno));
//		startActivity(callIntent);
    	
		stopService(new Intent(getApplicationContext(), CameraService.class));
//		startService(new Intent(getApplicationContext(), RecordingService.class));
	}
}