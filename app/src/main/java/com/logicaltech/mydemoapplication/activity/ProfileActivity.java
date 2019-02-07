package com.logicaltech.mydemoapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.logicaltech.mydemoapplication.R;

import utility.Constant;

public class ProfileActivity extends AppCompatActivity
{
    TextView TV_total_Balance;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TV_total_Balance = (TextView) findViewById(R.id.tv_totalbalance);
        TV_total_Balance.setText(Constant.TOTAL_BALANCE);
    }
}
