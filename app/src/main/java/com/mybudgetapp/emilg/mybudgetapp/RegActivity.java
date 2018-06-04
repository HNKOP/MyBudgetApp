package com.mybudgetapp.emilg.mybudgetapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.mybudgetapp.emilg.mybudgetapp.HTTP.HttpTask;

import org.json.JSONObject;

public class RegActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_layout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setTitle("Регистрация");
    }

    public void buttonConfirmRegOnClick(View view) {
        EditText editnewlogin = (EditText)  findViewById(R.id.edit_newlogin);
        EditText editnewpass = (EditText)  findViewById(R.id.edit_newpass);
        EditText editnewpassagain = (EditText)  findViewById(R.id.edit_newpass_again);
        String login = editnewlogin.getText().toString();
        String pass = editnewpass.getText().toString();
        String passagain = editnewpassagain.getText().toString();
        try
        {
            if(pass.equals(passagain))
            {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Login",login);
                jsonObject.put("Password",pass);
                HttpTask httpTask = new HttpTask();
                httpTask.execute("/api/reg/","POST",jsonObject.toString());

                String res = httpTask.get();
                res = res.replace("\\\"","\"");
                res = res.substring(1,res.length()-1);

                JSONObject jsonAnswer = new JSONObject(res);
                Toast.makeText(this, jsonAnswer.getString("msg"), Toast.LENGTH_SHORT).show();
                if(jsonAnswer.getString("status").equals("1"))
                {
                    //Вход в профиль
                    Toast.makeText(this, "Вход в профиль", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {

        }
    }
}
