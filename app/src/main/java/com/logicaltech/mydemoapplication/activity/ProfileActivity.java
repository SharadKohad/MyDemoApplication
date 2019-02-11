package com.logicaltech.mydemoapplication.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.logicaltech.mydemoapplication.R;

import utility.Constant;
import utility.SessionManeger;

public class ProfileActivity extends AppCompatActivity
{
    TextView TV_total_Balance;
    LinearLayout LinearLayout_ViewProfile,LinearLayout_AccountDetail,LinearLayout_Recharge_History,LinearLayout_Withdrawal_History;
    ImageView imageView_BackArrow;
    Button btn_logout;
    SessionManeger sessionManeger;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sessionManeger = new SessionManeger(getApplicationContext());
        init();


    }

    public void init()
    {
        TV_total_Balance = (TextView) findViewById(R.id.tv_totalbalance);
        LinearLayout_ViewProfile = (LinearLayout) findViewById(R.id.linear_layout_view_profile);
        LinearLayout_AccountDetail = (LinearLayout) findViewById(R.id.linear_layout_Account_list);
        LinearLayout_Recharge_History = (LinearLayout) findViewById(R.id.linear_layout_recharge_history);
        LinearLayout_Withdrawal_History = (LinearLayout) findViewById(R.id.linear_layout_withdrawal_history);
        imageView_BackArrow = (ImageView) findViewById(R.id.img_back_arrow_profile);
        btn_logout = (Button) findViewById(R.id.button_logout);
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

        LinearLayout_Withdrawal_History.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ProfileActivity.this,WithdrawalHistActivity.class);
                startActivity(intent);
            }
        });
        imageView_BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ProfileActivity.this)
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
        });

    }
}
