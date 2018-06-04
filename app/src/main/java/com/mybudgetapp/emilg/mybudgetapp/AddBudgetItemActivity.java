package com.mybudgetapp.emilg.mybudgetapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mybudgetapp.emilg.mybudgetapp.HTTP.HttpTask;

import org.json.JSONObject;

import java.util.ArrayList;

public class AddBudgetItemActivity extends AppCompatActivity {
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor;
    static final int RESULT_SPEECH = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_budgetitem_layout);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedPref = getSharedPreferences("GlobalPref",MODE_PRIVATE);
        editor = sharedPref.edit();
        setTitle("Добавление");
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

    public void onClickRecord(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,"ru-RU");

        try {

            startActivityForResult(intent, RESULT_SPEECH);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Opps! Your device doesn’t support Speech to Text",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    EditText editText = (EditText) findViewById(R.id.add_budgetitem_nameview);
                    EditText valueText = (EditText) findViewById(R.id.add_budgetitem_value);
                    String anw = text.get(0).toString();
                    Double value;
                    if(isDouble(anw))
                    {
                        value = Double.parseDouble(anw);
                        valueText.setText(value.toString());
                    }
                    else
                    {
                        editText.setText(anw);
                    }
                }break;
            }
        }
    }

    boolean isDouble(String str)
    {
        try
        {
            Double.parseDouble(str);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    public void onClickReturn(View view) {
        Intent intent = new Intent(getApplicationContext(),BudgetActivity.class);
        startActivity(intent);
    }
}
