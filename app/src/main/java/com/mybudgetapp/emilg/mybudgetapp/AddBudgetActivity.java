package com.mybudgetapp.emilg.mybudgetapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mybudgetapp.emilg.mybudgetapp.HTTP.HttpTask;

import org.json.JSONObject;

public class AddBudgetActivity extends AppCompatActivity {
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_budget_layout);

        sharedPref = getSharedPreferences("GlobalPref",MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public void addBudgetOnClick(View view) {
        EditText nameedit = (EditText) findViewById(R.id.add_budget_new_name_editview);
        String budgetname = nameedit.getText().toString();
        int owner_id =  sharedPref.getInt("PROFILE_ID", 0);
        try
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Name",budgetname);
            jsonObject.put("Owner_Id",owner_id);
            HttpTask httpTask = new HttpTask();
            httpTask.execute("/api/addbudget/","POST",jsonObject.toString());

            String res = httpTask.get();
            res = res.replace("\\\"","\"");
            res = res.substring(1,res.length()-1);
            JSONObject jsonAnswer = new JSONObject(res);

            if(jsonAnswer.getString("status").equals("1"))
            {
                Toast.makeText(this, "Новый счет создан", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {

        }
    }
}
