package com.logicaltech.mydemoapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.logicaltech.mydemoapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utility.Constant;
import utility.SessionManeger;

public class ProfileDetailActivity extends AppCompatActivity
{
    SessionManeger sessionManeger;
    EditText ET_Name,ET_Email,ET_MobileNo,ET_City;
    Button Btn_Profile_Save,Btn_Change_Password;
    private String userId,userMobile,userName,userEmail,city,memberId;
    ImageView IV_Back_Arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);
        sessionManeger = new SessionManeger(getApplicationContext());

        init();

        HashMap<String, String> hashMap = sessionManeger.getUserDetails();
        userId = hashMap.get(SessionManeger.KEY_ID);
        userMobile = hashMap.get(SessionManeger.KEY_PHONE);
        userName = hashMap.get(SessionManeger.KEY_NAME);
        userEmail = hashMap.get(SessionManeger.KEY_EMAIL);
        city = hashMap.get(SessionManeger.CITY);
        memberId = hashMap.get(SessionManeger.MEMBER_ID);

        ET_Name.setText(userName);
        ET_Email.setText(userEmail);
        ET_MobileNo.setText(userMobile);
        ET_City.setText(city);


    }

    public void init()
    {
        ET_Name = (EditText) findViewById(R.id.EditText_ProfileName);
        ET_Email = (EditText) findViewById(R.id.EditText_ProfileEmailId);
        ET_MobileNo = (EditText) findViewById(R.id.EditText_PhoneNumber);
        ET_City = (EditText) findViewById(R.id.EditText_City);
        Btn_Profile_Save = (Button) findViewById(R.id.button_profile_save);
        Btn_Change_Password = (Button) findViewById(R.id.button_change_password);
        IV_Back_Arrow = (ImageView) findViewById(R.id.img_back_arrow_profile_detail);

        Btn_Profile_Save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                putEditProfile(memberId,ET_Name.getText().toString(),ET_Email.getText().toString(),ET_MobileNo.getText().toString(),ET_City.getText().toString());
            }
        });

        IV_Back_Arrow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        Btn_Change_Password.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ProfileDetailActivity.this,ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    public void putEditProfile(final String memberId,final String name,final String emailId,final String mobileno,  final String city)
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        //  String url = Constant.URL+"addSignUp"; // <----enter your post url here
        String url = Constant.URL+"editProfile?MemberID="+memberId+"&Name="+name+"&Email="+emailId+"&MobileNo="+mobileno+"&City="+city;
        StringRequest MyStringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("PROFILE UPDATED SUCCESSFULLY."))
                    {
                        Toast.makeText(ProfileDetailActivity.this,"Profile Update",Toast.LENGTH_SHORT).show();
                        sessionManeger.createSession(userId,userName,userEmail,userMobile,memberId,city);
                    }
                    else
                    {
                        Toast.makeText(ProfileDetailActivity.this,"Profile update fail",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error)
            {
                //This code is executed if there is an error.
                String message= "";
                if (error instanceof ServerError)
                {
                    message = "The server could not be found. Please try again after some time!!";
                }
                else if (error instanceof TimeoutError)
                {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                System.out.println("error........"+error);
                //This code is executed if there is an error.
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept","application/json");
                headers.put("Content-Type","application/json");
                return headers;
            }
        };
        MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyRequestQueue.add(MyStringRequest);
    }
}
