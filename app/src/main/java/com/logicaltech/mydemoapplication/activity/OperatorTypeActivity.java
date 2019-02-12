package com.logicaltech.mydemoapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.logicaltech.mydemoapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.Operator_Adapter;
import model.Account_Model;
import model.Operator_Model;
import utility.Constant;

public class OperatorTypeActivity extends AppCompatActivity
{
    RecyclerView RecyclerView_Operator_Model;
    GridLayoutManager mGridLayoutManagerBrand;
    ArrayList<Operator_Model> arrayList =new ArrayList<>();
    ImageView imageView_Back_Arrow;
    String operatortype ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_type);
        RecyclerView_Operator_Model = (RecyclerView) findViewById(R.id.recycler_view_opretor_type);
        imageView_Back_Arrow = (ImageView) findViewById(R.id.img_back_arrow_operator_list);
        mGridLayoutManagerBrand = new GridLayoutManager(OperatorTypeActivity.this, 2);
        RecyclerView_Operator_Model.setLayoutManager(mGridLayoutManagerBrand);

        imageView_Back_Arrow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        operatortype = getIntent().getExtras().getString("operatortype");

        operatorType(operatortype);
    }

    public void operatorType(final String operatortype)
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        //  String url = Constant.URL+"addSignUp"; // <----enter your post url here
        String url = Constant.URL+"getOperatorByType?RechargeType="+operatortype;
        JsonArrayRequest MyStringRequest = new JsonArrayRequest(Request.Method.POST, url, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                try
                {
                    arrayList.clear();
                    for (int i = 0; i < response.length(); i++)
                    {
                        JSONObject jsonObject2 = response.getJSONObject(i);
                        String Type = jsonObject2.getString("Type");
                        String Operator = jsonObject2.getString("Operator");
                        String Code = jsonObject2.getString("Code");
                        String Icon = jsonObject2.getString("Icon");
                        String status = jsonObject2.getString("status");

                        Operator_Model model = new Operator_Model();
                        model.setType(Type);
                        model.setOperate(Operator);
                        model.setCode(Code);
                        model.setIcon(Icon);
                        model.setStatus(status);

                        arrayList.add(model);
                    }
                    Operator_Adapter operator_adapter = new Operator_Adapter(arrayList,getApplicationContext());
                    RecyclerView_Operator_Model.setAdapter(operator_adapter);
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
        })
        {
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
