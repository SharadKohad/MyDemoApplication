package com.logicaltech.mydemoapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.logicaltech.mydemoapplication.R;

public class AccountDetailActivity extends AppCompatActivity
{
    ImageView img_Back_Arrow_Accounts_detail;
    EditText ET_Account_Name,ETAccount_No,ET_Account_Type,ET_Bank_Name,ET_Ifsc_Code,ET_Branch_Name,ET_MobileNo;
    String token="0";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        img_Back_Arrow_Accounts_detail = (ImageView) findViewById(R.id.img_back_arrow_account_detail);

        ET_Account_Name = (EditText) findViewById(R.id.edit_text_Account_Name);
        ETAccount_No = (EditText) findViewById(R.id.edit_text_account_no);
        ET_Account_Type = (EditText) findViewById(R.id.edit_text_account_type);
        ET_Bank_Name = (EditText) findViewById(R.id.edit_text_bank_name);
        ET_Ifsc_Code = (EditText) findViewById(R.id.edit_text_ifsc_code);
        ET_Branch_Name = (EditText) findViewById(R.id.edit_text_branch_name);
        ET_MobileNo = (EditText) findViewById(R.id.edit_text_mobile_no);

        token = getIntent().getExtras().getString("token");
        if (token.equals("1"))
        {
            ET_Account_Name.setText(getIntent().getExtras().getString("accoun_name"));
            ETAccount_No.setText(getIntent().getExtras().getString("accoun_no"));
            ET_Account_Type.setText(getIntent().getExtras().getString("accoun_type"));
            ET_Bank_Name.setText(getIntent().getExtras().getString("bank_name"));
            ET_Ifsc_Code.setText(getIntent().getExtras().getString("ifsc_code"));
            ET_Branch_Name.setText(getIntent().getExtras().getString("branck_name"));
            ET_MobileNo.setText(getIntent().getExtras().getString("mobile_no"));
        }
        else
        {

        }

        img_Back_Arrow_Accounts_detail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}
