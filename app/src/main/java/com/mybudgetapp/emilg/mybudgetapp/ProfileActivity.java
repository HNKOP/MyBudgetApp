package com.mybudgetapp.emilg.mybudgetapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mybudgetapp.emilg.mybudgetapp.Adapters.RecycleBudgetAdapter;
import com.mybudgetapp.emilg.mybudgetapp.HTTP.HttpTask;
import com.mybudgetapp.emilg.mybudgetapp.Items.BudgetItem;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    ImageView imageView;
    private final int reqcode = 1;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<BudgetItem> list = new ArrayList<>();

    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        imageView = mCollapsingToolbarLayout.findViewById(R.id.backdrop);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setAvatar();

        mRecyclerView =  (RecyclerView) findViewById(R.id.budget_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        sharedPref = getSharedPreferences("GlobalPref",MODE_PRIVATE);
        editor = sharedPref.edit();

        try
        {
            initBudgets();

            //list.add(new BudgetItem(1,"Один","Два",100d));

            mAdapter = new RecycleBudgetAdapter(list);
            mRecyclerView.setAdapter(mAdapter);


            mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                    mRecyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, final int position) {
                     TextView textViewId = (TextView) view.findViewById(R.id.budget_item_id);
                      Toast.makeText(ProfileActivity.this,textViewId.getText().toString() , Toast.LENGTH_SHORT).show();
                    editor.putInt("BUDGET_ID", Integer.valueOf(textViewId.getText().toString()));
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(),BudgetActivity.class);
                    startActivity(intent);

                }

                @Override
                public void onLongClick(View view, int position) {
                    Toast.makeText(getApplicationContext(), "Long press on position :"+position,
                            Toast.LENGTH_LONG).show();
                }
            }));
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }




    }

    public void initBudgets() {

        try
        {
            HttpTask httpTask = new HttpTask();
            int owner_id =  sharedPref.getInt("PROFILE_ID", 0);
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("Owner_Id",owner_id);

            httpTask.execute("/api/getbudgetsbyid/","POST",jsonObject.toString());

            String res = httpTask.get();



            res = res.replace("\\\"","\"");
            res = res.substring(1,res.length()-1);
            JSONArray jsonArray = new JSONArray(res);
            for(int i = 0; i < jsonArray.length(); i++)
            {
                list.add(new BudgetItem(jsonArray.getJSONObject(i).getInt("Id")
                        ,jsonArray.getJSONObject(i).getString("Name"),"НЕИЗВ",jsonArray.getJSONObject(i).getDouble("Count")));
            }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setAvatar() {

        mCollapsingToolbarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickphoto = new Intent(Intent.ACTION_PICK);
                pickphoto.setType("image/*");
                startActivityForResult(pickphoto, reqcode);
            }
        });
        File imgFile = new File(this.getCacheDir().toString() + "/avatar.png");

        if (imgFile.exists()) {

            try {
                final Uri imageUri = Uri.fromFile(imgFile);
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Drawable dr = Drawable.createFromStream(imageStream, "avatar.png");

                imageView.setImageDrawable(dr);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case reqcode:
                if(resultCode == RESULT_OK){
                    try {


                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        Drawable dr = Drawable.createFromStream(imageStream,"avatar.png");
                        imageView.setImageDrawable(dr);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        SavePicture(this.getCacheDir().toString(),imageUri);


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }}
    private String SavePicture(String folderToSave, Uri uri)
    {

        try {
            File file = new File(folderToSave,"avatar.png"); // создать уникальное имя для файла

            final int chunkSize = 1024;  // We'll read in one kB at a time
            byte[] imageData = new byte[chunkSize];

            try {
                InputStream in = getContentResolver().openInputStream(uri);
                OutputStream out = new FileOutputStream(file);  // I'm assuming you already have the File object for where you're writing to

                int bytesRead;
                while ((bytesRead = in.read(imageData)) > 0) {
                    out.write(Arrays.copyOfRange(imageData, 0, Math.max(0, bytesRead)));
                }
                in.close();
                out.close();
            } catch (Exception ex) {

            }


            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(),  file.getName()); // регистрация в фотоальбоме
            Toast.makeText(getApplicationContext(),"Сохранено",Toast.LENGTH_LONG).show();
        }
        catch (Exception e) // здесь необходим блок отслеживания реальных ошибок и исключений, общий Exception приведен в качестве примера
        {
            return e.getMessage();
        }
        return "";
    }

    public void onProfileFABClc(View view) {

        Intent intent = new Intent(this,AddBudgetActivity.class);
        startActivity(intent);
    }
}
