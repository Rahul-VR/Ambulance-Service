package com.example.ambulance_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements JsonResponse{

    Button b1;
    EditText e1,e2;
    String username,password;
    TextView tv1;
    public static String logid,user_type;
    SharedPreferences sh,sh1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.etunm);
        e2=(EditText)findViewById(R.id.etpass);
        tv1=(TextView)findViewById(R.id.tvuserreg);
        b1=(Button)findViewById(R.id.btnlogin);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=e1 .getText() .toString();
                password=e2 .getText() .toString();


                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse)Login.this;
                String q="/login?username=" + username + "&password=" + password;
                q = q.replace(" ", "%20");
                JR.execute(q);



//              Toast.makeText(getApplicationContext(),"username : "+username+"\npassword : "+password, Toast.LENGTH_LONG).show();

            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),User_registration.class));


            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                logid = ja1.getJSONObject(0).getString("login_id");
                user_type = ja1.getJSONObject(0).getString("user_type");

                SharedPreferences.Editor e = sh.edit();
                e.putString("log_id", logid);
                e.commit();
                startService(new Intent(getApplicationContext(),LocationService.class) );
                if(user_type.equals("user"))
                {
                    Toast.makeText(getApplicationContext(),"Login Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), User_home.class));
                }

                if(user_type.equals("ambulance"))
                {
                    Toast.makeText(getApplicationContext(),"Login Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Ambulance_home.class));
                }


            }

            else {
                Toast.makeText(getApplicationContext(), "Login failed invalid username and password", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
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
        Intent b=new Intent(getApplicationContext(),IPSetting.class);
        startActivity(b);
    }
}