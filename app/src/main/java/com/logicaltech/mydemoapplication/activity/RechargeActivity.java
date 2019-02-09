package com.logicaltech.mydemoapplication.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class RechargeActivity extends AppCompatActivity
{
    ImageView IV_Back_Arrow,IV_Contact_Access;
    Button btn_recharge;
    private ProgressBar progress_bar;
    private View parent_view;
    TextInputEditText textInputEditTextMobileNumber;
    TextInputLayout textinputlayout_opertor;
    private static final int PERMISSION_REQUEST_CONTACT=0;
    TextInputEditText textInputEditText_operator,TextInputEditText_Amount;
    String token="0",memberId;
    SessionManeger sessionManeger;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        IV_Back_Arrow = (ImageView) findViewById(R.id.reacharg_back_arrow);
        btn_recharge = (Button) findViewById(R.id.btn_processrecharge);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        textinputlayout_opertor = (TextInputLayout)findViewById(R.id.textinputlayout_opertor);
        parent_view = findViewById(android.R.id.content);
        IV_Contact_Access = (ImageView) findViewById(R.id.contact_access);
        textInputEditTextMobileNumber = (TextInputEditText) findViewById(R.id.et_mobilenumber);
        textInputEditText_operator =(TextInputEditText) findViewById(R.id.tiet_operator);
        TextInputEditText_Amount = (TextInputEditText) findViewById(R.id.textinputedittext_amount);
        sessionManeger = new SessionManeger(getApplicationContext());

        HashMap<String, String> hashMap = sessionManeger.getUserDetails();
        memberId = hashMap.get(SessionManeger.MEMBER_ID);

        token = getIntent().getExtras().getString("token");

        if (token.equals("1"))
        {
            textInputEditText_operator.setText(getIntent().getExtras().getString("operator"));
        }

        IV_Back_Arrow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(RechargeActivity.this,DashBoardActivity.class);
                startActivity(intent);
            }
        });

        btn_recharge.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                searchAction();
            }
        });

        IV_Contact_Access.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                askForContactPermission();
            }
        });

        textinputlayout_opertor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(RechargeActivity.this,OperatorTypeActivity.class);
                intent.putExtra("operatortype","1");
                startActivity(intent);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK)
        {
            Uri contactData = data.getData();
            Cursor c =  getContentResolver().query(contactData, null, null, null, null);
            if (c.moveToFirst())
            {
                String phoneNumber="",emailAddress="";
                String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                //http://stackoverflow.com/questions/866769/how-to-call-android-contacts-list   our upvoted answer

                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if ( hasPhone.equalsIgnoreCase("1"))
                    hasPhone = "true";
                else
                    hasPhone = "false" ;

                if (Boolean.parseBoolean(hasPhone))
                {
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,null, null);
                    while (phones.moveToNext())
                    {
                        phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    phones.close();
                }

                //mainActivity.onBackPressed();
                // Toast.makeText(mainactivity, "go go go", Toast.LENGTH_SHORT).show();

               // tvname.setText("Name: "+name);
              //  tvphone.setText("Phone: "+phoneNumber);
               // tvmail.setText("Email: "+emailAddress);
                textInputEditTextMobileNumber.setText(""+phoneNumber);
                Log.d("curs", name + " num" + phoneNumber + " " + "mail" + emailAddress);
            }
            c.close();
        }
    }

    private void searchAction()
    {
        progress_bar.setVisibility(View.VISIBLE);
        btn_recharge.setAlpha(0f);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                progress_bar.setVisibility(View.GONE);
                btn_recharge.setAlpha(1f);
                registration(memberId,"1",textInputEditText_operator.getText().toString(),TextInputEditText_Amount.getText().toString(),textInputEditTextMobileNumber.getText().toString());
            }
        }, 1000);
    }

    private void getContact()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 1);
    }
    public void askForContactPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_CONTACT);
            }else
            {
                getContact();
            }
        }
        else
        {
            getContact();
        }
    }


    public void registration(final String member_Id, final String recharge_type, final String operator, final String amount, final String number)
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Constant.URL+"addRecharge?MemberID="+member_Id+"&RechargeType="+recharge_type+"&Operator="+operator+"&Amount="+amount+"&Number="+number+"&DeviceType=Android";
        StringRequest MyStringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("SUCCESS"))
                    {
                        Intent intent = new Intent(RechargeActivity.this,DashBoardActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(RechargeActivity.this,""+status,Toast.LENGTH_SHORT).show();
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
