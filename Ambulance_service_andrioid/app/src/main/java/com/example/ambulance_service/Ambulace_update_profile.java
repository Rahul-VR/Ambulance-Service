package com.example.ambulance_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Ambulace_update_profile extends AppCompatActivity implements JsonResponse {
    EditText e1, e2, e3, e4, e5, e6;
    Button b1;
    String ambulance_id, driver, place, val, phone, email, latitude, longitude;
    public static String log_id, am_id;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulace_update_profile);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        log_id = sh.getString("log_id", "");

        e1 = (EditText) findViewById(R.id.driver);
        e2 = (EditText) findViewById(R.id.place);
        e3 = (EditText) findViewById(R.id.phone);
        e4 = (EditText) findViewById(R.id.email);
//        e5=(EditText) findViewById(R.id.lati);
//        e6=(EditText) findViewById(R.id.longi);
        b1 = (Button) findViewById(R.id.profile);



        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Ambulace_update_profile.this;
        String q = "/viewprofile?lid=" + sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                driver = e1.getText().toString();
                ambulance_id = e1.getText().toString();

                place = e2.getText().toString();
                phone = e3.getText().toString();
                email = e4.getText().toString();
                latitude = e4.getText().toString();
                longitude = e4.getText().toString();

                if (driver.equalsIgnoreCase("")) {

                    e1.setError(" Enter Driver Name");
                    e1.setFocusable(true);
                } else if (place.equalsIgnoreCase("")) {
                    e2.setError("Ente City Name");
                    e2.setFocusable(true);
                } else if (email.equalsIgnoreCase("")) {
                    e4.setError("Enter Email Id");
                    e4.setFocusable(true);
                } else if (phone.equalsIgnoreCase("")) {
                    e3.setError("Enter Phone Number ");
                    e3.setFocusable(true);
                } else {


                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Ambulace_update_profile.this;
                    String q = "/Ambulace_update_profile?driver=" + driver + "&place=" + place + "&phone=" + phone + "&email=" + email + "&saddr=" + LocationService.lati + "" + "," + LocationService.logi + "&log_id=" + log_id;
                    // String q = "/Ambulace_update_profile?driver=" + driver + "&place=" + place + "&phone=" + phone + "&email=" + email+"&am_id=" +am_id;

                    q = q.replace(" ", "%20");
                    JR.execute(q);

                }
            }
        });

    }

    @Override
    public void response(JSONObject jo) {

        try {
            String method = jo.getString("method");

            if (method.equalsIgnoreCase("viewprofile")) {
                String status = jo.getString("status");
                Log.d("pearl", status);

                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

//                    Toast.makeText(getApplicationContext(), "hi" + ja1.getJSONObject(0).getString("firstname"), Toast.LENGTH_LONG).show();


                    e1.setText(ja1.getJSONObject(0).getString("driver"));
                    e2.setText(ja1.getJSONObject(0).getString("place"));
//                    e7.setText(ja1.getJSONObject(0).getString("place"));
                    e3.setText(ja1.getJSONObject(0).getString("phone"));
                   e4.setText(ja1.getJSONObject(0).getString("email"));

                    ambulance_id = ja1.getJSONObject(0).getString("ambulance_id");


                    SharedPreferences.Editor e = sh.edit();
                    //e.putString("log_id", logid);
                    e.commit();
                }
            }
            if (method.equalsIgnoreCase("Ambulace_update_profile")) {
                try {
                    String status = jo.getString("status");
                    Log.d("pearl", status);


                    if (status.equalsIgnoreCase("success")) {
                        Toast.makeText(getApplicationContext(), " SUCCESS", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), Ambulance_home.class));

                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }


    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Ambulance_home.class);
        startActivity(b);
    }
}
