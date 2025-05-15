package com.example.ambulance_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Ambulance_view_image extends AppCompatActivity implements AdapterView.OnItemClickListener, JsonResponse {
    ListView lv1;
    String[] request_id,image,details,amount,status,date;
    SharedPreferences sh;
    public static String request_ids ,upload_ids,log_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_view_image);
        lv1 = (ListView) findViewById(R.id.lvtrav);
        lv1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        log_id=sh.getString("log_id","");

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Ambulance_view_image.this;
        String q = "/Ambulance_view_image";
        q = q.replace(" ", "%20");
        JR.execute(q);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            if (method.equalsIgnoreCase("Ambulance_view_image")) {
                String status = jo.getString("status");
                Log.d("pearl", status);
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                if (status.equalsIgnoreCase("success")) {

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");


                    image = new String[ja1.length()];
                    request_id = new String[ja1.length()];





                    for (int i = 0; i < ja1.length(); i++) {


                        image[i] = ja1.getJSONObject(i).getString("image");
                        request_id[i] = ja1.getJSONObject(i).getString("request_id");



                    }
                    Custimage clist = new Custimage(this, image);
                    lv1.setAdapter(clist);


                } else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
//			if(method.equalsIgnoreCase("buyprod"))
//			{
//				String status=jo.getString("status");
//				Toast.makeText(getApplicationContext(),status, Toast.LENGTH_LONG).show();
//				if(status.equalsIgnoreCase("success"))
//				{
//					Toast.makeText(getApplicationContext(),"Your order is submitted!", Toast.LENGTH_LONG).show();
//				}
//				else{
//					Toast.makeText(getApplicationContext(),"Your order is not submitted", Toast.LENGTH_LONG).show();
//				}
//			}
        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();



    }
}
}