package com.logicaltech.mydemoapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.logicaltech.mydemoapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.Account_Adapter;
import model.Account_Model;
import utility.Constant;
import utility.SessionManeger;

public class AccountListActivity extends AppCompatActivity
{
    RecyclerView RecyclerView_Account_List;
    GridLayoutManager mGridLayoutManagerBrand;
    ArrayList<Account_Model> arrayList =new ArrayList<>();
    SessionManeger sessionManeger;
    ImageView img_Back_Arrow_Accounts_List;
    Button btn_add_new_account;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);
        sessionManeger = new SessionManeger(getApplicationContext());
        HashMap<String, String> hashMap = sessionManeger.getUserDetails();
        String member_id = hashMap.get(SessionManeger.MEMBER_ID);
        String userMobile = hashMap.get(SessionManeger.KEY_PHONE);
        RecyclerView_Account_List = (RecyclerView) findViewById(R.id.recycler_view_account_list);
        img_Back_Arrow_Accounts_List = (ImageView) findViewById(R.id.img_back_arrow_account_list);
        btn_add_new_account = (Button) findViewById(R.id.button_add_bank_account);
        mGridLayoutManagerBrand = new GridLayoutManager(AccountListActivity.this, 1);
        RecyclerView_Account_List.setLayoutManager(mGridLayoutManagerBrand);

        img_Back_Arrow_Accounts_List.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        btn_add_new_account.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AccountListActivity.this,AccountDetailActivity.class);
                intent.putExtra("token","0");
                startActivity(intent);
            }
        });

        AccountList(member_id,userMobile);
    }

    public void AccountList(final String memberId,final String mobileNo)
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
       // String url = Constant.URL+"getBankAccByMobileNo?MemberID="+memberId+"&mobile="+mobileNo;
        String url = Constant.URL+"getAccountListByMemberID?MemberID="+memberId;
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
                        String ac_no = jsonObject2.getString("accountNumber");
                        String ac_name = jsonObject2.getString("accountName");
                        String bk_name = jsonObject2.getString("bankName");
                        String bk_branch = jsonObject2.getString("bankBranch");
                        String bk_ifsc = jsonObject2.getString("bankIfsc");
                        String ac_type = jsonObject2.getString("accountType");
                        String bank_mobile_no = jsonObject2.getString("bankMobileNo");
                       // String Pin_Code = jsonObject2.getString("Pin_Code");
                       // String Bank_Address = jsonObject2.getString("Bank_Address");
                      //  String srno = jsonObject2.getString("srno");
                        String beneficiaryid = jsonObject2.getString("beneficiaryID");
                        String beneficiaryid_corporate = jsonObject2.getString("beneficiaryID_Corporate");
                        String agent_id = jsonObject2.getString("agentID");

                        Account_Model model = new Account_Model();
                        model.setAc_no(ac_no);
                        model.setAc_name(ac_name);
                        model.setBk_name(bk_name);
                        model.setBk_branch(bk_branch);
                        model.setBk_ifsc(bk_ifsc);
                        model.setAc_type(ac_type);
                        model.setBank_mobile_no(bank_mobile_no);
                      //  model.setPin_Code(Pin_Code);
                     //   model.setBank_Address(Bank_Address);
                     //   model.setSrno(srno);
                        model.setBeneficiaryid(beneficiaryid);
                        model.setBeneficiaryid_corporate(beneficiaryid_corporate);
                        model.setAgent_id(agent_id);
                        arrayList.add(model);
                    }
                    Account_Adapter account_adapter = new Account_Adapter(arrayList,getApplicationContext());
                    RecyclerView_Account_List.setAdapter(account_adapter);
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
