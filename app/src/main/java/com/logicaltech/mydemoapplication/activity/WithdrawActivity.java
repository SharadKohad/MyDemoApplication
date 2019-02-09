package com.logicaltech.mydemoapplication.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import adapter.Account_Adapter;
import model.Account_Model;
import model.MobileNumber;
import utility.Constant;
import utility.SessionManeger;

public class WithdrawActivity extends AppCompatActivity
{
    TextView textView_Total_Balance;
    LinearLayout linearLayout_mobile_no;
    SessionManeger sessionManeger;
    String member_id;
    RecyclerView recyclerView_Mobile_No;
    GridLayoutManager mGridLayoutManagerBrand;
    EditText ET_mobile_no;
    ArrayList<MobileNumber> arrayList =new ArrayList<>();
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        sessionManeger = new SessionManeger(getApplicationContext());
        HashMap<String, String> hashMap = sessionManeger.getUserDetails();
        member_id = hashMap.get(SessionManeger.MEMBER_ID);

        textView_Total_Balance = (TextView)findViewById(R.id.tv_withdraw_totalbalance);
        ET_mobile_no = (EditText) findViewById(R.id.EditText_Mobileno_withdraw);
        textView_Total_Balance.setText(Constant.TOTAL_BALANCE);
        linearLayout_mobile_no = (LinearLayout) findViewById(R.id.linear_layout_mobile_number);

        ET_mobile_no.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showCustomDialog();
            }
        });
    }

    private void showCustomDialog()
    {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_phone_number);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ImageView imageView = (ImageView) dialog.findViewById(R.id.imageview_close_dialog);
        recyclerView_Mobile_No = (RecyclerView)dialog.findViewById(R.id.recycler_view_mobile_no);
        mGridLayoutManagerBrand = new GridLayoutManager(WithdrawActivity.this, 1);
        recyclerView_Mobile_No.setLayoutManager(mGridLayoutManagerBrand);

        mobileNumberList(member_id);

        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void mobileNumberList(final String memberId)
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Constant.URL+"getMobileNos?MemberID="+memberId;
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
                        String request_mobile_no = jsonObject2.getString("request_mobile_no");

                        MobileNumber model = new MobileNumber();
                        model.setMobile(request_mobile_no);
                        arrayList.add(model);
                    }
                    Mobile_Adapter mobile_adapter = new Mobile_Adapter(arrayList,getApplicationContext());
                    recyclerView_Mobile_No.setAdapter(mobile_adapter);
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

    class Mobile_Adapter extends RecyclerView.Adapter<Mobile_Adapter.RecyclerViewHolder>
    {
        public ArrayList<MobileNumber> orderList;
        public Context mContext;
        public Mobile_Adapter(ArrayList<MobileNumber> orderList , Context context)
        {
            this.orderList = orderList;
            mContext = context;
        }

        @Override
        public Mobile_Adapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mobile_no_layout, parent, false);
            return new Mobile_Adapter.RecyclerViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final Mobile_Adapter.RecyclerViewHolder holder, final int position)
        {
            final MobileNumber account_model = orderList.get(position);
            holder.TV_AccountName.setText(account_model.getMobile());
            holder.relativeLayout_Account_List.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ET_mobile_no.setText(account_model.getMobile());
                    dialog.dismiss();
                }
            });
        }
        @Override
        public int getItemCount()
        {
            return orderList.size();
        }
        public class RecyclerViewHolder extends RecyclerView.ViewHolder
        {
            TextView TV_AccountName;
            LinearLayout relativeLayout_Account_List;
            public RecyclerViewHolder(View itemView)
            {
                super(itemView);
                TV_AccountName = (TextView) itemView.findViewById(R.id.text_view_mobile_name_list);
                relativeLayout_Account_List = (LinearLayout)itemView.findViewById(R.id.linear_layout_mobile_list);
            }
        }
    }
}
