package com.logicaltech.mydemoapplication;

import android.database.SQLException;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqlServerActivity extends AppCompatActivity
{
    Button btnConnect, btnAdd;
    TextView tv;
    EditText etFirst, etLast;
    Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_server);

        tv = (TextView) findViewById(R.id.tvDs);
        etFirst = (EditText)findViewById(R.id.etFirstName);
        etLast = (EditText)findViewById(R.id.etLastName);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setEnabled(false);

        btnConnect = (Button) findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                danhSach();
                btnConnect.setEnabled(false);
                btnAdd.setEnabled(true);
            }
        });

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ketnoi();
                PreparedStatement comm;
                try
                {
                    comm = conn.prepareStatement("insert into Employees(" + "firstname, lastname) values(?,?)");
                    comm.setString(1, etFirst.getText().toString());
                    comm.setString(2, etLast.getText().toString());
                    comm.executeUpdate();
                }
                catch (SQLException e)
                {
                    tv.setText(e.toString());
                }
                catch (java.sql.SQLException e)
                {
                    e.printStackTrace();
                }
                danhSach();
            }
        });
    }
    public void ketnoi()
    {
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("" + "jdbc:jtds:sqlserver://172.16.160.81/northwind;instance=SQL2008;" + "user=sa;password=sa;");
        }
        catch (Exception e)
        {
            tv.setText(e.toString());
        }
    }
    public void danhSach()
    {
        ketnoi();
        Statement comm;
        try
        {
            comm = conn.createStatement();
            ResultSet rs = comm.executeQuery("Select EmployeeID, Firstname From Employees");
            String msg = "";
            while (rs.next())
            {
                msg += "\nID: " + rs.getInt("EmployeeID") + " Name: " + rs.getString("Firstname");
            }
            tv.setText(msg);
        }
        catch (SQLException e)
        {
            tv.setText(e.toString());
        }
        catch (java.sql.SQLException e)
        {
            e.printStackTrace();
        }
    }
}
