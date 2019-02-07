package com.logicaltech.mydemoapplication.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.google.android.gms.common.SignInButton;
import com.google.gson.JsonObject;
import com.logicaltech.mydemoapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utility.Constant;
import utility.SessionManeger;

public class SignInActivity extends AppCompatActivity
{
    LinearLayout linearLayoutSignUp;
    FloatingActionButton fab_signin;
    TextInputEditText TIET_email_id,TIET_password;
    ProgressBar progressBar;
    SessionManeger sessionManeger;
    TextView TVforgotpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        sessionManeger = new SessionManeger(getApplicationContext());
        init();
    }
    public void init()
    {
        linearLayoutSignUp = (LinearLayout)findViewById(R.id.llsign_up_for_account);
        TIET_email_id = (TextInputEditText)findViewById(R.id.tiet_userid_signin);
        TIET_password = (TextInputEditText)findViewById(R.id.tiet_password_signin);
        fab_signin = (FloatingActionButton) findViewById(R.id.fab_signin);
        progressBar = (ProgressBar) findViewById(R.id.progress_barsignin);
        TVforgotpassword = (TextView) findViewById(R.id.forgotpassword);
        linearLayoutSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SignInActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

        fab_signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                signIn();
            }
        });

        TVforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
    }

    public void signIn()
    {
            String email_id = TIET_email_id.getText().toString();
            if (email_id.equals(""))
            {
                Toast.makeText(this,"Please enter valid email",Toast.LENGTH_SHORT).show();
            }
            else
            {
                String password = TIET_password.getText().toString();
                if (password.equals(""))
                {
                    Toast.makeText(this,"Please enter valid password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    signInVolly(email_id,password);
                }
            }
    }

    public void signInVolly(final String userId, final String Password)
    {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        //  String url = Constant.URL+"addSignUp"; // <----enter your post url here
        String url = Constant.URL+"getSignIn?UserID="+userId+"&Password="+Password;
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("SUCCESS"))
                    {
                        String userId = jsonObject.getString("UserID");
                        String email = jsonObject.getString("Email");
                        String mobileNo = jsonObject.getString("Mobile_No");
                        String userName = jsonObject.getString("Memb_Name");
                        String memberId = jsonObject.getString("membercode");
                        sessionManeger.createSession(userId,userName,email,mobileNo,memberId);
                        Intent intent=new Intent(SignInActivity.this,DashBoardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(SignInActivity.this,"fail",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e)
                {
                    progressBar.setVisibility(View.INVISIBLE);
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


    private void showCustomDialog()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.forgot_password);
        dialog.setCancelable(true);
        final TextInputEditText textInputEditTextEmail;

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        textInputEditTextEmail = (TextInputEditText) dialog.findViewById(R.id.tiet_password_forgot);

        ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String forgotEmail = textInputEditTextEmail.getText().toString();
                if (forgotEmail.equals(""))
                {
                    Toast.makeText(SignInActivity.this,"Please enter valid email",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    forgotPassword(forgotEmail);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    public void forgotPassword(final String userId)
    {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        //  String url = Constant.URL+"addSignUp"; // <----enter your post url here
        String url = Constant.URL+"ForgotPassword?UserID="+userId;
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("SUCCESS"))
                    {
                        String userId = jsonObject.getString("username");
                        Toast.makeText(SignInActivity.this,"Link Send to your register email id",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(SignInActivity.this,"fail",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e)
                {
                    progressBar.setVisibility(View.INVISIBLE);
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
