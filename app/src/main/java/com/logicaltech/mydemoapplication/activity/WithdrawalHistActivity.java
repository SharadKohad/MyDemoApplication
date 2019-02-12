package com.logicaltech.mydemoapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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

import adapter.WithDraw_History_Adapter;
import model.Withdraw_History_Model;
import utility.Constant;
import utility.SessionManeger;

public class WithdrawalHistActivity extends AppCompatActivity
{
    ImageView imageView_back_arrow;
    RecyclerView RecyclerView_Withdrawl_History;
    GridLayoutManager mGridLayoutManagerBrand;
    ArrayList<Withdraw_History_Model> arrayList =new ArrayList<>();
    SessionManeger sessionManeger;
    private String member_id;
    Button btn_withdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal_hist);
        sessionManeger = new SessionManeger(getApplicationContext());
        HashMap<String, String> hashMap = sessionManeger.getUserDetails();
        member_id = hashMap.get(SessionManeger.MEMBER_ID);

        imageView_back_arrow = (ImageView) findViewById(R.id.img_back_arrow_withdraw_history);
        RecyclerView_Withdrawl_History = (RecyclerView) findViewById(R.id.recycler_view_withdrawl_history);
        btn_withdraw = (Button) findViewById(R.id.button_add_withdraw);
        mGridLayoutManagerBrand = new GridLayoutManager(WithdrawalHistActivity.this, 1);
        RecyclerView_Withdrawl_History.setLayoutManager(mGridLayoutManagerBrand);

        withdrawHistoryList(member_id);

        imageView_back_arrow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        btn_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(WithdrawalHistActivity.this,WithdrawActivity.class);
                startActivity(intent);
            }
        });
    }

    public void withdrawHistoryList(final String memberId)
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        //  String url = Constant.URL+"addSignUp"; // <----enter your post url here
        String url = Constant.URL+"getWithdrawalHist?MemberID="+memberId;
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
                        String tdate = jsonObject2.getString("tdate");
                        String amount = jsonObject2.getString("amount");
                        String charges = jsonObject2.getString("charges");
                        String tds = jsonObject2.getString("tds");
                        String paid_amount = jsonObject2.getString("paid_amount");
                        String wstatus = jsonObject2.getString("wstatus");


                        Withdraw_History_Model model = new Withdraw_History_Model();
                        model.setDate(tdate);
                        model.setAmount(amount);
                        model.setCharges(charges);
                        model.setTds(tds);
                        model.setPaid_amount(paid_amount);
                        model.setStatus(wstatus);

                        arrayList.add(model);
                    }
                    /*Account_Adapter account_adapter = new Account_Adapter(arrayList,getApplicationContext());
                    RecyclerView_Account_List.setAdapter(account_adapter);*/

                    WithDraw_History_Adapter withDraw_history_adapter = new WithDraw_History_Adapter(arrayList,getApplicationContext());
                    RecyclerView_Withdrawl_History.setAdapter(withDraw_history_adapter);

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
