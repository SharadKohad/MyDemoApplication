package com.logicaltech.mydemoapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{
    ListView ListView1;
    Button button,buttonTree,ViewPager,checkout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView1= (ListView) findViewById(R.id.listview);
        button = (Button) findViewById(R.id.button_image);
        buttonTree = (Button) findViewById(R.id.button_tree);
        ViewPager = (Button) findViewById(R.id.button_imageview);
        checkout =(Button) findViewById(R.id.button_checkout);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<List<Hero>> call =api.getHeros();

        call.enqueue(new Callback<List<Hero>>()
        {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response)
            {
               List<Hero> heroes= response.body();
               String [] heroName = new String[heroes.size()];
               for (int i=0;i<heroes.size();i++)
               {
                   heroName[i] = heroes.get(i).getName();
               }
               ListView1.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,heroName));
            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),NotificationActivity.class);
                startActivity(intent);
            }
        });

        buttonTree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(intent);
            }
        });

        ViewPager.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),ViewPagerActivity.class);
                startActivity(intent);
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CheckOutActivity.class);
                startActivity(intent);
            }
        });
    }
}
