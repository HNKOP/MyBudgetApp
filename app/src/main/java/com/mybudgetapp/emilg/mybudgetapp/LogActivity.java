package com.mybudgetapp.emilg.mybudgetapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.mybudgetapp.emilg.mybudgetapp.HTTP.HttpTask;

import org.json.JSONObject;

public class LogActivity extends AppCompatActivity {

    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_layout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        sharedPref = getSharedPreferences("GlobalPref",MODE_PRIVATE);
        editor = sharedPref.edit();

    }

    public void buttonLoginOnClick(View view) {

        try
        {
            EditText editname = (EditText)  findViewById(R.id.editname);
            EditText editpass = (EditText)  findViewById(R.id.editpass);
            String login =  editname.getText().toString();
            String pass = editpass.getText().toString();
           // Toast.makeText(this, login + ":" + pass, Toast.LENGTH_SHORT).show();
            HttpTask httpTask = new HttpTask();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Login",login);
            jsonObject.put("Password",pass);
            httpTask.execute("/api/auth/","POST",jsonObject.toString());

            String res = httpTask.get();
            res = res.replace("\\\"","\"");
            res = res.substring(1,res.length()-1);


            JSONObject jsonAnswer = new JSONObject(res);
            Toast.makeText(this, jsonAnswer.getString("msg"), Toast.LENGTH_SHORT).show();
            if(jsonAnswer.getString("status").equals("1"))
            {
                //Вход в профиль
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("Login",login);
                HttpTask httpTask1 = new HttpTask();
                httpTask1.execute("/api/getuserid/","POST",jsonObject1.toString());

                String res1 = httpTask1.get();
                res1 = res1.replace("\\\"","\"");
                res1 = res1.substring(1,res1.length()-1);

                JSONObject jsonAnswer2 = new JSONObject(res1);
                if(jsonAnswer2.getString("status").equals("1"))
                {
                    editor.putInt("PROFILE_ID", jsonAnswer2.getInt("id"));
                    editor.apply();
                    Intent intent = new Intent(this,ProfileActivity.class);
                    startActivity(intent);
                }





            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public void buttonRegOnClick(View view) {
        Intent intent = new Intent(this,RegActivity.class);
        startActivity(intent);
    }
}
