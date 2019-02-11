package com.logicaltech.mydemoapplication.activity;

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

public class ChangePasswordActivity extends AppCompatActivity
{
    ImageView IV_Back_Arrow;
    Button btn_save_password;
    EditText ET_Old_Password,ET_NewPassword,ET_ConformPaaword;
    SessionManeger sessionManeger;
    String memberId;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        sessionManeger =new SessionManeger(getApplicationContext());
        IV_Back_Arrow = (ImageView) findViewById(R.id.img_back_arrow_change_password);
        btn_save_password = (Button) findViewById(R.id.button_forgotpassword_save);
        ET_Old_Password = (EditText) findViewById(R.id.edit_text_old_password);
        ET_NewPassword = (EditText) findViewById(R.id.edit_text_new_password);
        ET_ConformPaaword = (EditText) findViewById(R.id.edit_text_new_password_conform);

        HashMap<String, String> hashMap = sessionManeger.getUserDetails();

        memberId = hashMap.get(SessionManeger.MEMBER_ID);

        IV_Back_Arrow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        btn_save_password.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changePassword();
            }
        });
    }
    public void changePassword()
    {
        String oldPassword = ET_Old_Password.getText().toString();
        if (oldPassword.equals(""))
        {
            Toast.makeText(ChangePasswordActivity.this,"Please enter the Old Password",Toast.LENGTH_SHORT).show();
        }
        else
        {
            String newPassword = ET_NewPassword.getText().toString();
            if (newPassword.equals(""))
            {
                Toast.makeText(ChangePasswordActivity.this,"Please enter the New Password",Toast.LENGTH_SHORT).show();
            }
            else
            {
                String conformPassword = ET_ConformPaaword.getText().toString();
                if (conformPassword.equals(""))
                {
                    Toast.makeText(ChangePasswordActivity.this,"Password don,t match",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (newPassword.equals(conformPassword))
                    {
                        changePassword(memberId,oldPassword,newPassword);
                    }
                    else
                    {
                        Toast.makeText(ChangePasswordActivity.this,"Password don,t match",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    public void changePassword(final String memberId,final String oldPassword,final String newPawwrod)
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        //  String url = Constant.URL+"addSignUp"; // <----enter your post url here
        String url = Constant.URL+"ChangePassword?MemberID="+memberId+"&CurrentPass="+oldPassword+"&NewPass="+newPawwrod;
        StringRequest MyStringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("PASSWORD CHANGE SUCCESSFULLY."))
                    {
                        Toast.makeText(ChangePasswordActivity.this,"Change Password",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(ChangePasswordActivity.this,"fail",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept","application/json");
                headers.put("Content-Type","application/json");
                return headers;
            }

          /*  protected Map<String, String> getParams()
            {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("MobileNo", Mobileno);
                MyData.put("Email", EmailId);
                MyData.put("UserID",UserID);
                MyData.put("name", name);
                MyData.put("Password", Password);
                MyData.put("place", place);
                MyData.put("sponserID", sponserid);
                MyData.put("ip_address", ip_address);
                MyData.put("DeviceType", devicetype);
                return MyData;
            }*/
        };
        MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyRequestQueue.add(MyStringRequest);
    }
}
