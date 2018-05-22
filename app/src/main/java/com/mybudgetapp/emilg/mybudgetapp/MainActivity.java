package com.mybudgetapp.emilg.mybudgetapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mybudgetapp.emilg.mybudgetapp.HTTP.HttpTask;

import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonPushOnClick(View view) {
        //Toast.makeText(getApplicationContext(),"Button Clicked!",Toast.LENGTH_SHORT).show();
        HttpTask httpTask = new HttpTask();
        httpTask.execute("/api/values/","GET");
        try
        {
            String res = httpTask.get();
            //Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
            TextView greetingIdText = (TextView) findViewById(R.id.textView);
            greetingIdText.setText(res);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }


    public void buttonPushPostOnClick(View view) {
        //Пост запрос
        HttpTask httpTask = new HttpTask();
        httpTask.execute("/api/values/","POST","DATA");
        try
        {
            String res = httpTask.get();
          // Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
            TextView greetingIdText = (TextView) findViewById(R.id.textView);
            greetingIdText.setText(res);

        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }


    public void buttonLogOnClick(View view) {
        Intent intent = new Intent(this,LogActivity.class);
        startActivity(intent);
    }
}
