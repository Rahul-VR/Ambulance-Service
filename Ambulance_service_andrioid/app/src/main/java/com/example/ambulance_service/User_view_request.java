package com.example.ambulance_service;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class User_view_request extends AppCompatActivity implements AdapterView.OnItemClickListener , JsonResponse {
    ListView lv1;
    String [] request_id,firstname,lastname,ambulace_id,user_id,patient_id,place,phone,details,amount,latitude,longitude,date,statuss,val,driver;
    public static String req_id,lts,lgs,log_id,u_id,p_id,am_id,amounts,dates,status;
    SharedPreferences sh;
//    public static String log_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_request);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        log_id=sh.getString("log_id","");

        lv1=(ListView)findViewById(R.id.lvreq);
        lv1.setOnItemClickListener(this);


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_request.this;
        String q = "/User_view_request?log_id="+log_id;
        q=q.replace(" ","%20");
        JR.execute(q);

    }



    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            Log.d("pearl",method);
            if(method.equalsIgnoreCase("User_view_request")){
                String status=jo.getString("status");

                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    request_id=new String[ja1.length()];
//                    user_id=new String[ja1.length()];
                    patient_id=new String[ja1.length()];
                    ambulace_id=new String[ja1.length()];
//                    firstname=new String[ja1.length()];
//                    lastname=new String[ja1.length()];

                    driver=new String[ja1.length()];
                     phone=new String[ja1.length()];
                     place=new String[ja1.length()];
                    details=new String[ja1.length()];
                    amount=new String[ja1.length()];
                    date=new String[ja1.length()];
                    statuss=new String[ja1.length()];

                    latitude=new String[ja1.length()];
                    longitude=new String[ja1.length()];

                    val=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {

                        request_id[i]=ja1.getJSONObject(i).getString("request_id");
//                        user_id[i]=ja1.getJSONObject(i).getString("user_id");
                        patient_id[i]=ja1.getJSONObject(i).getString("patient_id");
                        ambulace_id[i]=ja1.getJSONObject(i).getString("ambulace_id");


//                        firstname[i]=ja1.getJSONObject(i).getString("firstname");
//                        lastname[i]=ja1.getJSONObject(i).getString("lastname");
                        driver[i]=ja1.getJSONObject(i).getString("driver");
                        phone[i]=ja1.getJSONObject(i).getString("phone");
                        place[i]=ja1.getJSONObject(i).getString("place");
                        details[i]=ja1.getJSONObject(i).getString("details");
                        amount[i]=ja1.getJSONObject(i).getString("amount");
                        date[i]=ja1.getJSONObject(i).getString("date");
                        statuss[i]=ja1.getJSONObject(i).getString("status");

                        latitude[i]=ja1.getJSONObject(i).getString("latitude");
                        longitude[i]=ja1.getJSONObject(i).getString("longitude");

//                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
//                        val[i]="driver :"+driver[i]+ "\nphone :"+phone[i] + "\nplace :"+place[i]+"\ndetails :"+details[i]+ "\namount :"+amount[i] + "\ndate :" +date[i]+"\nstatus :" +statuss[i];
                        CustomRequest a = new CustomRequest(this, driver, place,phone,details,amount,date,statuss);
                        lv1.setAdapter(a);

                    }
//




                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        req_id = request_id[i];
        lts = latitude[i];
        lgs = longitude[i];
//        u_id=user_id[i];
        p_id = patient_id[i];
        am_id = ambulace_id[i];
        amounts = amount[i];
        dates = date[i];
        status=statuss[i];


//        if (statuss.equals("approve")) {

        if (status.equals("approve")) {


            final CharSequence[] items = {"View Map", "Payment", "Rating", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(User_view_request.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {


                    if (items[item].equals("View Map")) {
                        String url = "http://www.google.com/maps?saddr=" + LocationService.lati + "" + "," + LocationService.logi + "" + "&&daddr=" + User_view_request.lts + "," + User_view_request.lgs;
                        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(in);
                    }

                        if (items[item].equals("Payment")) {
                        startActivity(new Intent(getApplicationContext(), User_make_payment.class));
//                    JsonReq JR=new JsonReq();
//                    JR.json_response=(JsonResponse) User_view_request.this;
//                    String q = "/User_make_payment?log_id="+log_id;
//                    q=q.replace(" ","%20");
//                    JR.execute(q);
                    }
                    if (items[item].equals("Rating")) {
//					JsonReq JR=new JsonReq();
//					JR.json_response=(JsonResponse) User_add_interests.this;
//					String q = "/user_add_interest?loginid="+Login.logid+"&type_id="+User_add_interests.type_ids;
//					q=q.replace(" ","%20");
//					JR.execute(q);
                        startActivity(new Intent(getApplicationContext(), Review.class));

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }


            });
            builder.show();
//	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //startActivityForResult(i, GALLERY_CODE);
        }
    }
//   }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),User_home.class);
        startActivity(b);
    }




}



