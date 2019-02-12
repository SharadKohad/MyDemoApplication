package com.logicaltech.mydemoapplication.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
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
import com.logicaltech.mydemoapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;
import utility.Constant;
import utility.SessionManeger;

public class DashBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    BannerSlider bannerSlider;
    List<Banner> banners=new ArrayList<>();
    LinearLayout CV_Mobile_Reacharge,CV_Eletricity_Recharge,LL_Flight_Booking,LL_Withdraw,LL_Utility,LL_Dth,LL_train,LL_Bus,LL_Hotel;
    Intent intent;
    SessionManeger sessionManeger;
    TextView TextViewUserName,TextViewUserEmail;
    String memberId;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sessionManeger = new SessionManeger(getApplicationContext());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        Menu nav_Menu = navigationView.getMenu();

        TextViewUserName = (TextView)  hView.findViewById(R.id.textviewprofilename);
        TextViewUserEmail = (TextView)  hView.findViewById(R.id.textviewprofileemailid);
        init();

        if (sessionManeger.checkLogin())
        {
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
        }
        else
        {
            HashMap<String, String> hashMap = sessionManeger.getUserDetails();
            String userId = hashMap.get(SessionManeger.KEY_ID);
            String userMobile = hashMap.get(SessionManeger.KEY_PHONE);
            String userName = hashMap.get(SessionManeger.KEY_NAME);
            String userEmail = hashMap.get(SessionManeger.KEY_EMAIL);
            memberId = hashMap.get(SessionManeger.MEMBER_ID);
          //  button_nav_signup.setVisibility(View.GONE);
            TextViewUserName.setVisibility(View.VISIBLE);
            TextViewUserEmail.setVisibility(View.VISIBLE);
            TextViewUserName.setText(userName);
            TextViewUserEmail.setText(userEmail);
            dashBoardData(memberId);
        }
        //add banner using resource drawable
        banners.add(new DrawableBanner(R.drawable.slider1));
        banners.add(new DrawableBanner(R.drawable.slider2));
        banners.add(new DrawableBanner(R.drawable.slider3));
        banners.add(new DrawableBanner(R.drawable.slider4));
        banners.add(new DrawableBanner(R.drawable.slider5));
        bannerSlider.setBanners(banners);
    }

    public void init()
    {
        bannerSlider = (BannerSlider) findViewById(R.id.banner_slider1);
        CV_Mobile_Reacharge = (LinearLayout) findViewById(R.id.linearlayout_mobile_reacharge);
        CV_Eletricity_Recharge = (LinearLayout) findViewById(R.id.linear_layout_electricity);
        LL_Flight_Booking = (LinearLayout) findViewById(R.id.linear_layout_flightbooking);
        LL_Withdraw = (LinearLayout) findViewById(R.id.linear_layout_withdraw);
        LL_Utility = (LinearLayout) findViewById(R.id.linear_layout_utility);
        LL_Dth = (LinearLayout) findViewById(R.id.linear_layout_DTH);
        LL_train = (LinearLayout) findViewById(R.id.linear_layout_train);
        LL_Bus = (LinearLayout) findViewById(R.id.linear_layout_Account_bus);
        LL_Hotel=(LinearLayout) findViewById(R.id.linear_layout_hotel);

        CV_Mobile_Reacharge.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(DashBoardActivity.this,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });

        LL_Hotel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(DashBoardActivity.this,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });

        CV_Mobile_Reacharge.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(DashBoardActivity.this,RechargeActivity.class);
                intent.putExtra("token","0");
                startActivity(intent);
            }
        });

        LL_train.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(DashBoardActivity.this,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });

        CV_Eletricity_Recharge.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
              //  intent = new Intent(DashBoardActivity.this,ElectricityRechargeActivity.class);
              //  startActivity(intent);
                Toast.makeText(DashBoardActivity.this,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });

        LL_Flight_Booking.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               // intent = new Intent(DashBoardActivity.this,FlightBookActivity.class);
               // startActivity(intent);
                Toast.makeText(DashBoardActivity.this,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });

        LL_Withdraw.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(DashBoardActivity.this,WithdrawActivity.class);
                startActivity(intent);
            }
        });

        LL_Utility.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(DashBoardActivity.this,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });

        LL_Dth.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(DashBoardActivity.this,DTHActivity.class);
                intent.putExtra("token","0");
                startActivity(intent);
            }
        });

        LL_Bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashBoardActivity.this,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_profile)
        {
            intent = new Intent(this,ProfileActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_withdraw)
        {
            intent = new Intent(this,WithdrawActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_accountlist)
        {
            intent = new Intent(this,AccountListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_recharge)
        {
            intent = new Intent(this,RechargeActivity.class);
            intent.putExtra("token","0");
            startActivity(intent);
        }
        else if (id == R.id.nav_logout)
        {
            new AlertDialog.Builder(DashBoardActivity.this)
                    .setMessage("Are you sure you want to logout?").setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            sessionManeger.logoutUser();
                            finish();
                        }
                    }).setNegativeButton("No", null).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void dashBoardData(final String memberId)
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Constant.URL+"getDashboardData?MemberID="+memberId;
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
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
                        String total_Balance = jsonObject.getString("Total_Balance");
                        Constant.TOTAL_BALANCE = total_Balance;
                        Toast.makeText(DashBoardActivity.this,"DashBoard Successfull",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(DashBoardActivity.this,"fail",Toast.LENGTH_SHORT).show();
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
