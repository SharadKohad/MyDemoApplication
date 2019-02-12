package com.logicaltech.mydemoapplication.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.logicaltech.mydemoapplication.R;

public class DTHActivity extends AppCompatActivity
{
    ImageView imageView_Back_Arrow;
    Button btn_dth_recharge;
    TextView TV_Operator;
    EditText ET_mobile_no,ET_amount;
    String token="0";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dth);
        imageView_Back_Arrow = (ImageView)findViewById(R.id.img_back_arrow_dth_recharge);
        btn_dth_recharge = (Button) findViewById(R.id.button_dth_recharge);
        TV_Operator = (TextView) findViewById(R.id.TextView_dth_operator);
        ET_mobile_no = (EditText) findViewById(R.id.EditText_dth_operator_mobile_no);
        ET_amount = (EditText) findViewById(R.id.EditText_dth_amount);

        token = getIntent().getExtras().getString("token");
        if (token.equals("1"))
        {
            TV_Operator.setText(getIntent().getExtras().getString("operator"));
        }

        imageView_Back_Arrow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(DTHActivity.this,DashBoardActivity.class);
                startActivity(intent);
            }
        });

        TV_Operator.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(DTHActivity.this,OperatorTypeActivity.class);
                intent.putExtra("operatortype","2");
                startActivity(intent);
            }
        });

        btn_dth_recharge.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dth_charge();
            }
        });

    }

    public void dth_charge()
    {
        String opertor_type = TV_Operator.getText().toString();
        if (opertor_type.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Please select operator type",Toast.LENGTH_SHORT).show();
        }
        else
        {
            String mobile_no = ET_mobile_no.getText().toString();
            if (mobile_no.equals(""))
            {
                Toast.makeText(getApplicationContext(),"Please enter mobile no",Toast.LENGTH_SHORT).show();
            }
            else
            {
                String amount = ET_amount.getText().toString();
                if (amount.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please enter amount",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    btn_dth_recharge.setAlpha(0f);
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            btn_dth_recharge.setAlpha(1f);
                            Toast.makeText(getApplicationContext(),"Recharge Cooming Soon",Toast.LENGTH_SHORT).show();
                        }
                    }, 1000);
                }
            }
        }
    }
}
