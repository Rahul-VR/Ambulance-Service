package com.example.ambulance_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Rating;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Ambulance_view_review extends AppCompatActivity implements  JsonResponse{
    ListView lv1;

    String[] review_id,rating,review,val,date, fname,lname,place,user_id;
    SharedPreferences sh;
    public static String log_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_view_review);
        lv1=(ListView) findViewById(R.id.lvrew);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        log_id=sh.getString("log_id","");

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Ambulance_view_review.this;
        String q = "/Ambulance_view_review?log_id=" + log_id ;
        q=q.replace(" ","%20");
        JR.execute(q);


    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            if (method.equalsIgnoreCase("Ambulance_view_review")) {
                String status = jo.getString("status");
                Log.d("pearl", status);
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                if (status.equalsIgnoreCase("success")) {

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    review_id = new String[ja1.length()];
                    user_id=  new String[ja1.length()];
                    fname = new String[ja1.length()];
                    lname = new String[ja1.length()];
                    place = new String[ja1.length()];
                    rating = new String[ja1.length()];
                    review = new String[ja1.length()];
                    date = new String[ja1.length()];



                    val = new String[ja1.length()];


                    for (int i = 0; i < ja1.length(); i++) {

                        review_id[i] = ja1.getJSONObject(i).getString("review_id");
                        rating[i] = ja1.getJSONObject(i).getString("rating");
                        review[i] = ja1.getJSONObject(i).getString("review");
                        date[i] = ja1.getJSONObject(i).getString("date");
                        user_id[i] = ja1.getJSONObject(i).getString("user_id");
                        fname[i] = ja1.getJSONObject(i).getString("firstname");
                        lname[i] = ja1.getJSONObject(i).getString("lastname");
                        place[i] = ja1.getJSONObject(i).getString("place");


//                        Toast.makeText(getApplicationContext(), val[i], Toast.LENGTH_SHORT).show();
//                        val[i] = "rating : " + rating[i] + " - " + review[i] + "\ndate" + date[i];


                    }
                    CustomRiview a = new CustomRiview(this,fname,lname,place ,rating, review,date);
                    lv1.setAdapter(a);


                } else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

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