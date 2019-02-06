package com.logicaltech.mydemoapplication.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.logicaltech.mydemoapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ElectricityRechargeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    Spinner spinner;
    ImageView IV_Back_Arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_recharge);
        spinner =(Spinner) findViewById(R.id.spinner);
        IV_Back_Arrow = (ImageView) findViewById(R.id.electricitybill_back_arrow);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this, R.array.india_states, android.R.layout.simple_spinner_item);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        IV_Back_Arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
