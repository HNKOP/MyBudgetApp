package com.mybudgetapp.emilg.mybudgetapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mybudgetapp.emilg.mybudgetapp.HTTP.HttpTask;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonPushOnClick(View view) {
        //Toast.makeText(getApplicationContext(),"Button Clicked!",Toast.LENGTH_SHORT).show();
        HttpTask httpTask = new HttpTask();
        httpTask.execute("http://192.168.1.38:58368/api/values/");
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
    }
}
