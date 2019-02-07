package com.logicaltech.mydemoapplication.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.logicaltech.mydemoapplication.MainActivity;
import com.logicaltech.mydemoapplication.R;

import java.util.HashMap;

import utility.SessionManeger;

public class SpashActivity extends AppCompatActivity
{
    Handler handler;
    LinearLayout li1,li2;
    Animation uptodown,downtoup;
    SessionManeger sessionManeger;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);
        li1 = (LinearLayout) findViewById(R.id.li1);
        li2 = (LinearLayout) findViewById(R.id.li2);
        sessionManeger = new SessionManeger(getApplicationContext());

        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        li1.setAnimation(uptodown);
        li2.setAnimation(downtoup);

        handler=new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (sessionManeger.checkLogin())
                {
                    Intent intent=new Intent(SpashActivity.this,SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent=new Intent(SpashActivity.this,DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },1000);
    }
}
