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

public class SpashActivity extends AppCompatActivity
{
    Handler handler;
    LinearLayout li1,li2;
    Animation uptodown,downtoup;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);
        li1 = (LinearLayout) findViewById(R.id.li1);
        li2 = (LinearLayout) findViewById(R.id.li2);

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
                Intent intent=new Intent(SpashActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}
