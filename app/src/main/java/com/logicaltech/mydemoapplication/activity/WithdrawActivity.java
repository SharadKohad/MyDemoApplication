package com.logicaltech.mydemoapplication.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.logicaltech.mydemoapplication.R;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.Account_Adapter;
import model.Account_Model;
import model.MobileNumber;
import utility.AsyncTaskResult;
import utility.Constant;
import utility.MySingalton;
import utility.SessionManeger;

public class WithdrawActivity extends AppCompatActivity
{
    TextView TV_title,textView_Total_Balance,TextView_TDS,TextView_Charges,TV_Paid_Amount;
    ImageView imageView_close;
    LinearLayout linearLayout_mobile_no,linearLayout_mobile_otp;
    SessionManeger sessionManeger;
    String member_id,user_email,user_name,mobile_no=null,token="0",account_no,kyc_status="N",otp="0";
    RecyclerView recyclerView_Mobile_No;
    GridLayoutManager mGridLayoutManagerBrand;
    EditText ET_mobile_no,ET_account_no,ET_Amount,ET_Otp;
    ArrayList<MobileNumber> arrayList =new ArrayList<>();
    ArrayList<Account_Model> arrayList1 =new ArrayList<>();
    WindowManager.LayoutParams lp;
    Button Btn_getotp,Btn_withDraw;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        sessionManeger = new SessionManeger(getApplicationContext());
        HashMap<String, String> hashMap = sessionManeger.getUserDetails();
        member_id = hashMap.get(SessionManeger.MEMBER_ID);
        user_name = hashMap.get(SessionManeger.KEY_NAME);
        user_email = hashMap.get(SessionManeger.KEY_EMAIL);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_phone_number);
        dialog.setCancelable(true);

        lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        textView_Total_Balance = (TextView)findViewById(R.id.tv_withdraw_totalbalance);
        ET_mobile_no = (EditText) findViewById(R.id.EditText_Mobileno_withdraw);
        textView_Total_Balance.setText(Constant.TOTAL_BALANCE);
        linearLayout_mobile_no = (LinearLayout) findViewById(R.id.linear_layout_mobile_number);
        ET_account_no = (EditText) findViewById(R.id.EditText_account_no_withdraw);
        ET_Amount = (EditText) findViewById(R.id.EditText_PhoneNumber);
        TextView_TDS = (TextView) findViewById(R.id.TV_tds);
        TextView_Charges = (TextView) findViewById(R.id.TV_Charges);
        TV_Paid_Amount = (TextView) findViewById(R.id.TV_paid_amount);
        imageView_close = (ImageView) dialog.findViewById(R.id.imageview_close_dialog);
        recyclerView_Mobile_No = (RecyclerView)dialog.findViewById(R.id.recycler_view_mobile_no);
        mGridLayoutManagerBrand = new GridLayoutManager(WithdrawActivity.this, 1);
        TV_title = (TextView) dialog.findViewById(R.id.TV_dialog_title);
        recyclerView_Mobile_No.setLayoutManager(mGridLayoutManagerBrand);
        ET_Otp = (EditText) findViewById(R.id.EditText_Mobileno_otp);
        Btn_getotp = (Button) findViewById(R.id.button_getotp);
        linearLayout_mobile_no = (LinearLayout) findViewById(R.id.linear_layout_mobile_otp);
        linearLayout_mobile_otp = (LinearLayout) findViewById(R.id.linear_layout_mobile_otp);
        Btn_withDraw = (Button) findViewById(R.id.button_withdrawle);

        ET_Amount.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String amount=s.toString();
                if (s.length()==0)
                {
                    ET_Amount.setError("Please Enter Amount");
                }
                else
                {
                    int intamount = Integer.parseInt(amount);
                    int charges,remaing=0;
                    if (kyc_status.equals("Y"))
                    {
                        charges = (intamount*5)/100;
                        remaing = intamount-(charges*2);
                    }
                    else
                    {
                        charges = (intamount*20)/100;
                        remaing = intamount-(charges*2);
                    }
                    TV_Paid_Amount.setText(""+remaing);
                    TextView_Charges.setText(""+charges);
                    TextView_TDS.setText(""+charges);
                }
            }
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        mobileNumberList(member_id);
        postKYC(member_id);

        ET_mobile_no.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showMobileNumberDialog();
            }
        });

        Btn_withDraw.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (otp.equals(ET_Otp.getText().toString()))
                {
                    putWithdraw(member_id,ET_mobile_no.getText().toString(),ET_account_no.getText().toString(),TV_Paid_Amount.getText().toString());
                }
                else
                {
                    Toast.makeText(WithdrawActivity.this,"Not Match",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ET_account_no.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                token="1";
                showMobileNumberDialog();
            }
        });

        Btn_getotp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                postGetOtp(ET_mobile_no.getText().toString(),user_email,user_name);
            }
        });

    }

    private void showMobileNumberDialog()
    {
        if (token.equals("0"))
        {
            TV_title.setText("Mobile No");
        }
        else
        {
            TV_title.setText("Account No");
            account_no_list(member_id,mobile_no);
        }

        imageView_close.setOnClickListener(new View.OnClickListener()
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

    public void account_no_list(final String memberId,final String mobile_no)
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Constant.URL+"getBankAccByMobileNo?mobile="+mobile_no+"&MemberID="+memberId;
        JsonArrayRequest MyStringRequest = new JsonArrayRequest(Request.Method.POST, url, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                try
                {
                    arrayList1.clear();
                    for (int i = 0; i < response.length(); i++)
                    {
                        JSONObject jsonObject2 = response.getJSONObject(i);
                        String ac_no = jsonObject2.getString("ac_no");
                        String bk_name = jsonObject2.getString("bk_name");

                        Account_Model model = new Account_Model();
                        model.setAc_no(ac_no);
                        model.setAc_name(bk_name);
                        arrayList1.add(model);
                    }
                    Account_Adapter account_adapter = new Account_Adapter(arrayList1,getApplicationContext());
                    recyclerView_Mobile_No.setAdapter(account_adapter);
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
                    mobile_no = account_model.getMobile();
                    ET_mobile_no.setText(mobile_no);
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

    class Account_Adapter extends RecyclerView.Adapter<Account_Adapter.RecyclerViewHolder>
    {
        public ArrayList<Account_Model> orderList;
        public Context mContext;
        public Account_Adapter(ArrayList<Account_Model> orderList , Context context)
        {
            this.orderList = orderList;
            mContext = context;
        }
        @Override
        public Account_Adapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mobile_no_layout, parent, false);
            return new Account_Adapter.RecyclerViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final Account_Adapter.RecyclerViewHolder holder, final int position)
        {
            final Account_Model account_model = orderList.get(position);
            holder.TV_AccountName.setText(account_model.getAc_no());
            holder.relativeLayout_Account_List.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    account_no = account_model.getAc_no();
                    ET_account_no.setText(account_no);
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

    public void postKYC(final String member_id)
    {
        String strurl= Constant.URL+"getKYCStatus?MemberID="+member_id;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, strurl, new Response.Listener<JSONObject>()
        {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    String status = response.getString("status");
                    if (status.equals("SUCCESS"))
                    {
                        kyc_status= response.getString("KYC_Status");
                        Toast.makeText(WithdrawActivity.this,"Success "+kyc_status,Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(WithdrawActivity.this,"Fail "+kyc_status,Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        System.out.println("error...");
                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headers = new HashMap<>();
                return headers;
            }
        };
        MySingalton.getInstance(getApplicationContext()).addRequestQueue(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void postGetOtp(final String mobile_no,final String user_email,final String member_name)
    {
        // String strurl=AppConstant.MAIN_URL+"api/ProductsExtended/"+p_id;
        String strurl= Constant.URL+"getOTP?codeType=W&MobileNo="+mobile_no+"&EmailID="+user_email+"&MemberName="+member_name;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, strurl, new Response.Listener<JSONObject>()
        {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    String status = response.getString("status");
                    if (status.equals("SUCCESS"))
                    {
                        linearLayout_mobile_otp.setVisibility(View.VISIBLE);
                        Btn_getotp.setVisibility(View.GONE);
                        Btn_withDraw.setVisibility(View.VISIBLE);
                        otp = response.getString("OTP");
                        System.out.println("OTP SENT:"+otp);
                    }
                    else
                    {
                        Toast.makeText(WithdrawActivity.this,"fail",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        System.out.println("error...");
                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headers = new HashMap<>();
                return headers;
            }
        };
        MySingalton.getInstance(getApplicationContext()).addRequestQueue(jsonObjectRequest);
    }


    public void putWithdraw(final String member_id, final String mobile_no, final String bank_id, final String amount)
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        //  String url = Constant.URL+"addSignUp"; // <----enter your post url here
        String url = Constant.URL+"addWithdrawal?MemberID="+member_id+"&Activestat=G&request_mobile_no="+mobile_no+"&bank_id="+bank_id+"&Amount="+amount+"&DeviceType=Android";
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
                        Toast.makeText(WithdrawActivity.this,"Withdraw: "+status,Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(WithdrawActivity.this,"Registration fail"+status,Toast.LENGTH_SHORT).show();
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
        };
        MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyRequestQueue.add(MyStringRequest);
    }

}
