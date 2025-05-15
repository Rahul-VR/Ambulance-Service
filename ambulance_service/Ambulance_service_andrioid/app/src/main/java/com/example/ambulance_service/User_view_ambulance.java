package com.example.ambulance_service;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class User_view_ambulance extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView lv1;
    String[] email, ambulance_id, patient_id, place, phone, details, amount, latitude, longitude, val, driver;
    public static String req_id, lts, lgs, log_id, u_id, p_id, am_id, amounts, dates;
    SharedPreferences sh;
//    public static String log_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_ambulance);
        lv1 = (ListView) findViewById(R.id.lvamb);
        lv1.setOnItemClickListener(this);


        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) User_view_ambulance.this;
        String q = "/User_view_ambulance?ambulance_id="+am_id;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            Log.d("pearl", method);
            if (method.equalsIgnoreCase("User_view_ambulance")) {
                String status = jo.getString("status");

                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                if (status.equalsIgnoreCase("success")) {

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    ambulance_id = new String[ja1.length()];
                   // Toast.makeText(User_view_ambulance.this, "dhfa"+ambulace_id.toString(), Toast.LENGTH_SHORT).show();

                    driver = new String[ja1.length()];
                    phone = new String[ja1.length()];
                    place = new String[ja1.length()];
                    email = new String[ja1.length()];


                    latitude = new String[ja1.length()];
                    longitude = new String[ja1.length()];

                    val = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {

//
                        ambulance_id[i] = ja1.getJSONObject(i).getString("ambulance_id");


//                        firstname[i]=ja1.getJSONObject(i).getString("firstname");
//                        lastname[i]=ja1.getJSONObject(i).getString("lastname");
                        driver[i] = ja1.getJSONObject(i).getString("driver");
                        phone[i] = ja1.getJSONObject(i).getString("phone");
                        place[i] = ja1.getJSONObject(i).getString("place");


                        email[i] = ja1.getJSONObject(i).getString("email");


                        latitude[i] = ja1.getJSONObject(i).getString("latitude");
                        longitude[i] = ja1.getJSONObject(i).getString("longitude");

//                        Toast.makeText(getApplicationContext(), val[i], Toast.LENGTH_SHORT).show();
//                        val[i] = "driver :" + driver[i] + "\nphone :" + phone[i] + "\nplace :" + place[i]  + "\nemail :" + email[i];


                    }
//                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, val);
//                    lv1.setAdapter(ar);
                    CustomUser a = new CustomUser(this, driver, place,phone,email);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        lts = latitude[i];
        lgs = longitude[i];
//        u_id=user_id[i];
//        p_id = patient_id[i];
        am_id = ambulance_id[i];


//        if (statuss.equals("accept")) {


        final CharSequence[] items = {"View Map", "Request", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_view_ambulance.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("View Map")) {
                    String url = "http://www.google.com/maps?saddr=" + LocationService.lati + "" + "," + LocationService.logi + "" + "&&daddr=" + User_view_ambulance.lts + "," + User_view_ambulance.lgs;
                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(in);
                }

                if (items[item].equals("Request")) {

                    startActivity(new Intent(getApplicationContext(), User_upload_files.class));
//                    JsonReq JR=new JsonReq();
//                    JR.json_response=(JsonResponse) User_view_request.this;
//                    String q = "/User_make_payment?log_id="+log_id;
//                    q=q.replace(" ","%20");
//                    JR.execute(q);
                }
//                if (items[item].equals("Rating")) {
////					JsonReq JR=new JsonReq();
////					JR.json_response=(JsonResponse) User_add_interests.this;
////					String q = "/user_add_interest?loginid="+Login.logid+"&type_id="+User_add_interests.type_ids;
////					q=q.replace(" ","%20");
////					JR.execute(q);
//                    startActivity(new Intent(getApplicationContext(), Review.class));
//
//                }
               else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();
//	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(i, GALLERY_CODE);
    }
    //    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),User_home.class);
        startActivity(b);
    }

    }