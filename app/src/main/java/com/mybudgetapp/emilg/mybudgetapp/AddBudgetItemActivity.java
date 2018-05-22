package com.mybudgetapp.emilg.mybudgetapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mybudgetapp.emilg.mybudgetapp.HTTP.HttpTask;

import org.json.JSONObject;

public class AddBudgetItemActivity extends AppCompatActivity {
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_budgetitem_layout);

        sharedPref = getSharedPreferences("GlobalPref",MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public void onConfirmNewBudgetItemClick(View view) {
        EditText nameview = (EditText) findViewById(R.id.add_budgetitem_nameview);
        EditText valueview = (EditText) findViewById(R.id.add_budgetitem_value);
        Spinner spinner = (Spinner) findViewById(R.id.add_budgetitem_spinner);
        try
        {
            HttpTask httpTask = new HttpTask();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Name",nameview.getText().toString());
            jsonObject.put("Value",Double.valueOf(valueview.getText().toString()));
            String spinnerans = spinner.getSelectedItem().toString();
            if(spinnerans.equals("Доход"))
            {
                jsonObject.put("Type",true);
            }
            else
            {
                jsonObject.put("Type",false);
            }
            jsonObject.put("Budget_Id",sharedPref.getInt("BUDGET_ID", 0));
            httpTask.execute("/api/budgetitem/","POST",jsonObject.toString());

            String res = httpTask.get();
            res = res.replace("\\\"","\"");
            res = res.substring(1,res.length()-1);


            JSONObject jsonAnswer = new JSONObject(res);
            Toast.makeText(this, jsonAnswer.getString("msg"), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
