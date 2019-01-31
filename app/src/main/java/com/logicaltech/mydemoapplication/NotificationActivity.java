package com.logicaltech.mydemoapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity
{
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        //getting the notification message
        String message=getIntent().getStringExtra("message");
        textView.setText(message);
    }

    //i want to change this file
}
