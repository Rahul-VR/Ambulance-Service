package com.example.ambulance_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class User_complaint extends AppCompatActivity implements JsonResponse{
    EditText e1;
    Button b1;
    ListView l1;
    String complaint;
    String[] cid,comp,reply,val,date;
    SharedPreferences sh;
    public static String log_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_complaint);
        e1=(EditText) findViewById(R.id.etct);
        l1=(ListView) findViewById(R.id.lv6);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        log_id=sh.getString("log_id","");

        JsonReq jr = new JsonReq();
        jr.json_response = (JsonResponse) User_complaint.this;
        String q = "/complaint?log_id="+log_id;
        q.replace("", "%20");
        jr.execute(q);
        b1=(Button) findViewById(R.id.btnct);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                complaint = e1.getText().toString();
                if (complaint.equalsIgnoreCase("")) {
                    e1.setError("Enter your Complaint");
                    e1.setFocusable(true);
                } else {
                    JsonReq jr = new JsonReq();
                    jr.json_response = (JsonResponse) User_complaint.this;
                    String q = "/addcomplaint?log_id=" + log_id + "&Complaint=" + complaint;

                    q.replace("", "%20");
                    jr.execute(q);

//                    Toast.makeText(getApplicationContext(),"log_id : "+log_id,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),User_complaint.class));

                }

            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            Log.d("pearl",method);


            if(method.equalsIgnoreCase("view")) {

                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja = (JSONArray) jo.getJSONArray("data");

                    cid = new String[ja.length()];
                    date = new String[ja.length()];

                    comp = new String[ja.length()];
                    reply = new String[ja.length()];
                    val = new String[ja.length()];


                    for (int i = 0; i < ja.length(); i++) {
                        date[i] = ja.getJSONObject(i).getString("date");
                        comp[i] = ja.getJSONObject(i).getString("complaint");
                        reply[i] = ja.getJSONObject(i).getString("reply");
//                        val[i] = "\ndate : " + date[i] + "\nComplaint : " + comp[i] + "\nReply : " + reply[i];
                    }
                    CustomComplaint a = new CustomComplaint(this, date, comp,reply);
                    l1.setAdapter(a);

//                    l1.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, val));
                    {

                    }
                }
            }
                    if (method.equalsIgnoreCase("view_complaint")) {
                        try {
                            String status = jo.getString("status");
                            Log.d("pearl", status);


                            if (status.equalsIgnoreCase("success")) {
                                Toast.makeText(getApplicationContext(), " Complaint Send", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), User_complaint.class));

                            }

                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }


    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),User_home.class);
        startActivity(b);
    }
}