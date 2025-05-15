package com.example.ambulance_service;
//import org.ksoap2.SoapEnvelope;
//import org.ksoap2.serialization.SoapObject;
//import org.ksoap2.serialization.SoapSerializationEnvelope;
//import org.ksoap2.transport.HttpTransportSE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.widget.Toast;

/*
<receiver android:name="com.example.womensapp.SettingsContentObserver">
- <intent-filter>
  <action android:name="android.media.VOLUME_CHANGED_ACTION" /> 
  <action android:name="android.intent.action.BOOT_COMPLETED" /> 
  </intent-filter>
  </receiver>
*/

public class SettingsContentObserver extends BroadcastReceiver implements OnInitListener 
{
	//public static int flags=0;
	
	int previousVolume;
	int flg=0;
	Handler hnd;
	Context context;

	 SharedPreferences sh1;
	@Override
	public void onReceive(Context arg0, Intent intent) {
		
		context=arg0;
		SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
		
		sh1=PreferenceManager.getDefaultSharedPreferences(context);
		Editor ed= sh.edit();
		ed.putString("flg", "0");
//      ed.putString("count", "0");
 		ed.commit();
		hnd=new Handler();

		if(intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
		{
			Toast.makeText(context, "Started App", Toast.LENGTH_SHORT).show();
			Editor ed1= sh.edit();
			ed1.putString("flg", "0");
			ed1.commit();
		} else {
		  try {
			  hnd.removeCallbacks(rn);
		  } catch(Exception ex){}
		  
		  hnd.postDelayed( rn,3000);
		  int k=0;
		  try {
		  k= Integer.parseInt(sh.getString("count", "0"));
		  k++;
		  Editor ed1=sh.edit();
		  ed1.putString("count", k+"");
		  ed1.commit();
		  } catch(Exception ex) {}
		  
		  Log.d("count------------------", sh.getString("count", "0"));
//		  Toast.makeText(context,"Media button click detected "+sh.getString("count", "0"), Toast.LENGTH_SHORT).show();
		  if((sh.getString("flg", "0").equalsIgnoreCase("0")) && sh.getString("count", "0").equalsIgnoreCase("6"))
	      {
			  Editor ed1= sh.edit();
	           ed1.putString("flg", "1");
	           ed1.putString("count", "0");
	           ed1.commit();
		
	           int volume = (Integer)intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_VALUE");
             
              Log.d("hai.......",  "Action : "+ intent.getAction() + " / volume : "+volume);
              
             // context.stopService(new Intent(context, CameraService.class));
             context.startService(new Intent(context,CameraService.class));
            //  context.startService(new Intent(context,RecordingService.class));
             	  
              Toast.makeText(context,"Start Emergency......", Toast.LENGTH_SHORT).show();
	      }
	      }
		  
	  }
	



	private void startService(Intent intent) {
		// TODO Auto-generated method stub
		
	}



	private void startActivity(Intent intent1) {
		// TODO Auto-generated method stub
		
	}



	Runnable rn=new Runnable() {
		@Override
		public void run() {
			SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
			Editor ed=sh.edit();
		    ed.putString("count", "0");
			ed.commit();
			Log.d("clear", "done");
		}
	};

	@Override
	public void onInit(int arg0) {
		// TODO Auto-generated method stub
	}
}
