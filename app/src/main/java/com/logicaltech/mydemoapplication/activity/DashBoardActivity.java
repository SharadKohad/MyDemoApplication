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

import com.logicaltech.mydemoapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;
import utility.SessionManeger;

public class DashBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    BannerSlider bannerSlider;
    List<Banner> banners=new ArrayList<>();
    LinearLayout CV_Mobile_Reacharge,CV_Eletricity_Recharge,LL_Flight_Booking;
    Intent intent;
    SessionManeger sessionManeger;
    TextView TextViewUserName,TextViewUserEmail;
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

        if (sessionManeger.checkLogin())
        {
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
           /* nav_Menu.findItem(R.id.nav_profile).setVisible(false);
            button_nav_signup.setVisibility(View.VISIBLE);*/
        }
        else
        {
            HashMap<String, String> hashMap = sessionManeger.getUserDetails();
            String userId = hashMap.get(SessionManeger.KEY_ID);
            String userMobile = hashMap.get(SessionManeger.KEY_PHONE);
            String userName = hashMap.get(SessionManeger.KEY_NAME);
            String userEmail = hashMap.get(SessionManeger.KEY_EMAIL);
          //  button_nav_signup.setVisibility(View.GONE);
            TextViewUserName.setVisibility(View.VISIBLE);
            TextViewUserEmail.setVisibility(View.VISIBLE);
            TextViewUserName.setText(userName);
            TextViewUserEmail.setText(userEmail);

        }


        bannerSlider = (BannerSlider) findViewById(R.id.banner_slider1);
        CV_Mobile_Reacharge = (LinearLayout) findViewById(R.id.linearlayout_mobile_reacharge);
        CV_Eletricity_Recharge = (LinearLayout) findViewById(R.id.linear_layout_electricity);
        LL_Flight_Booking = (LinearLayout) findViewById(R.id.linear_layout_flightbooking);
        //add banner using resource drawable
        banners.add(new DrawableBanner(R.drawable.slider1));
        banners.add(new DrawableBanner(R.drawable.slider2));
        banners.add(new DrawableBanner(R.drawable.slider3));
        bannerSlider.setBanners(banners);

        CV_Mobile_Reacharge.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(DashBoardActivity.this,RechargeActivity.class);
                startActivity(intent);
            }
        });

        CV_Eletricity_Recharge.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(DashBoardActivity.this,ElectricityRechargeActivity.class);
                startActivity(intent);
            }
        });

        LL_Flight_Booking.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(DashBoardActivity.this,FlightBookActivity.class);
                startActivity(intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
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
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera)
        {
            intent = new Intent(this,SignInActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            intent = new Intent(this,SignupActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout)
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
}
