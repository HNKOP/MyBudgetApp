package com.mybudgetapp.emilg.mybudgetapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mybudgetapp.emilg.mybudgetapp.Adapters.RecycleBudgetItemAdapter;
import com.mybudgetapp.emilg.mybudgetapp.HTTP.HttpTask;
import com.mybudgetapp.emilg.mybudgetapp.Items.BudgetSubItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BudgetActivity extends AppCompatActivity {
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor;



    List<BudgetSubItem> poslist = new ArrayList<>();
    List<BudgetSubItem> neglist = new ArrayList<>();

    private RecyclerView mRecyclerViewPos;
    private RecyclerView.Adapter mAdapterPos;
    private RecyclerView.LayoutManager mLayoutManagerPos;

    private RecyclerView mRecyclerViewNeg;
    private RecyclerView.Adapter mAdapterNeg;
    private RecyclerView.LayoutManager mLayoutManagerNeg;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_layout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Счет");

        sharedPref = getSharedPreferences("GlobalPref",MODE_PRIVATE);
        editor = sharedPref.edit();

        mRecyclerViewPos =  (RecyclerView) findViewById(R.id.pos_recycle_view);
        mRecyclerViewPos.setHasFixedSize(true);

        mLayoutManagerPos = new LinearLayoutManager(this);
        mRecyclerViewPos.setLayoutManager(mLayoutManagerPos);

        mRecyclerViewNeg =  (RecyclerView) findViewById(R.id.neg_recycle_view);
        mRecyclerViewNeg.setHasFixedSize(true);

        mLayoutManagerNeg = new LinearLayoutManager(this);
        mRecyclerViewNeg.setLayoutManager(mLayoutManagerNeg);


        initItems();

        mAdapterPos = new RecycleBudgetItemAdapter(poslist);
        mRecyclerViewPos.setAdapter(mAdapterPos);

        mAdapterNeg = new RecycleBudgetItemAdapter(neglist);
        mRecyclerViewNeg.setAdapter(mAdapterNeg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.budgetitem_menu,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_share:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initItems() {
        try
        {
            int Budget_Id = sharedPref.getInt("BUDGET_ID", 0);
            HttpTask httpTask = new HttpTask();

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("Budget_Id",Budget_Id);

            httpTask.execute("/api/getbudgetitemsbybudgetid/","POST",jsonObject.toString());

            String res = httpTask.get();



            res = res.replace("\\\"","\"");
            res = res.substring(1,res.length()-1);
            JSONArray jsonArray = new JSONArray(res);
            for(int i = 0; i < jsonArray.length(); i++)
            {
                boolean flag = jsonArray.getJSONObject(i).getBoolean("Type");
                if(flag)
                {
                    poslist.add(new BudgetSubItem(jsonArray.getJSONObject(i).getInt("Id"),
                            jsonArray.getJSONObject(i).getString("Name"),
                            jsonArray.getJSONObject(i).getDouble("Value"),
                            jsonArray.getJSONObject(i).getBoolean("Type")));
                }
                else
                {
                    neglist.add(new BudgetSubItem(jsonArray.getJSONObject(i).getInt("Id"),
                            jsonArray.getJSONObject(i).getString("Name"),
                            jsonArray.getJSONObject(i).getDouble("Value"),
                            jsonArray.getJSONObject(i).getBoolean("Type")));
                }

            }

            TextView possum = (TextView) findViewById(R.id.textview_pos_sum);
            TextView negsum = (TextView) findViewById(R.id.textview_neg_sum);
            double posvalue = 0,negvalue = 0;
            for(int i = 0; i < poslist.size(); i++)
            {
                posvalue += poslist.get(i).getValue();
            }
            for(int i = 0; i < neglist.size(); i++)
            {
                negvalue += neglist.get(i).getValue();
            }
            possum.setText(String.valueOf(posvalue));
            negsum.setText(String.valueOf(negvalue));
            // JSONObject jsonAnswer = new JSONObject(res);

            // if(jsonAnswer.getString("status").equals("1"))
            //  {
            //     Toast.makeText(this, jsonAnswer.getString("msg"), Toast.LENGTH_SHORT).show();
//
            //  }

        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    public void onAddNewBudgetItemFABClc(View view) {
        Intent intent = new Intent(this,AddBudgetItemActivity.class);
        startActivity(intent);
    }
}
