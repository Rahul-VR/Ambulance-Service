package com.example.ambulance_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class User_registration extends AppCompatActivity implements JsonResponse {

    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12;
    Button b1;
    String firstname,lastname,place,phone,email,username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        e1=(EditText) findViewById(R.id.fname);
        e2=(EditText) findViewById(R.id.lname);
        e7=(EditText) findViewById(R.id.place);
        e3=(EditText) findViewById(R.id.phone);
        e4=(EditText) findViewById(R.id.email);
        e5=(EditText) findViewById(R.id.uname);
        e6=(EditText) findViewById(R.id.pwd) ;
        b1=(Button) findViewById(R.id.ubtn);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firstname=e1.getText().toString();
                lastname=e2.getText().toString();

                phone=e3.getText().toString();
                email=e4.getText().toString();

                place=e7.getText().toString();

                username=e5.getText().toString();
                password=e6.getText().toString();

                if(firstname.equalsIgnoreCase("")){
                    e1.setError("enter your first name");
                    e1.setFocusable(true);
                }
                else if (lastname.equalsIgnoreCase("")){
                    e2.setError("enter your last name");
                    e2.setFocusable(true);
                }
                else if(phone.length()!=10)
                {
                    e3.setError("enter your phone no. in correct format");
                    e3.setFocusable(true);
                }
                else if (email.equalsIgnoreCase(""))
                {
                    e4.setError("enter the email id");
                    e4.setFocusable(true);
                }
                else if (place.equalsIgnoreCase("")){
                    e7.setError("enter your city name");
                    e7.setFocusable(true);
                }
                else if(username.equalsIgnoreCase("")){
                    e5.setError("enter your username");
                    e5.setFocusable(true);
                }
                else if(password.equalsIgnoreCase(""))
                {
                    e6.setError("Enter Your Password");
                    e6.setFocusable(true);
                }
                else {


                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) User_registration.this;
                    String q = "/User_registration?firstname=" + firstname + "&lastname=" + lastname + "&place=" + place + "&phone=" + phone + "&email=" + email + "&username=" + username + "&password=" + password;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

//                    Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {

                Toast.makeText(getApplicationContext(),"Registration Succesfull",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Login.class));



            }
        }
        catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }
}