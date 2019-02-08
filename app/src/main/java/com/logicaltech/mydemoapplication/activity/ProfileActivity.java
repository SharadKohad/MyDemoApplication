package com.logicaltech.mydemoapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.logicaltech.mydemoapplication.R;

import utility.Constant;

public class ProfileActivity extends AppCompatActivity
{
    TextView TV_total_Balance;
    LinearLayout LinearLayout_ViewProfile,LinearLayout_AccountDetail,LinearLayout_Recharge_History;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TV_total_Balance = (TextView) findViewById(R.id.tv_totalbalance);
        LinearLayout_ViewProfile = (LinearLayout) findViewById(R.id.linear_layout_view_profile);
        LinearLayout_AccountDetail = (LinearLayout) findViewById(R.id.linear_layout_Account_list);
        LinearLayout_Recharge_History = (LinearLayout) findViewById(R.id.linear_layout_recharge_history);
        TV_total_Balance.setText(Constant.TOTAL_BALANCE);

        LinearLayout_ViewProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ProfileActivity.this,ProfileDetailActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout_AccountDetail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ProfileActivity.this,AccountListActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout_Recharge_History.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ProfileActivity.this,RechargeHistoryActivity.class);
                startActivity(intent);
            }
        });

    }
}
