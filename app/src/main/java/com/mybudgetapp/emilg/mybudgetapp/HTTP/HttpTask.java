package com.mybudgetapp.emilg.mybudgetapp.HTTP;

import android.os.AsyncTask;

import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import com.mybudgetapp.emilg.mybudgetapp.Greeting;
import com.mybudgetapp.emilg.mybudgetapp.R;

import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HttpTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... path) {

        String content = "no value";
        try{
            switch(path[1])
            {
                case "GET": content = getContent(path[0]);
                    break;
                case "POST": content = postContent(path[0],path[2]);
                    break;
            }

        }
        catch (IOException ex){
            content = ex.getMessage();
        }

        return content;
    }



    @Override
    protected void onPostExecute(String content) {


    }

    private String getContent(String path) throws IOException {
        BufferedReader reader=null;
        try {
            URL url=new URL(path);
            HttpURLConnection c=(HttpURLConnection)url.openConnection();
            c.setRequestMethod("GET");
            c.setReadTimeout(10000);
            c.connect();
            reader= new BufferedReader(new InputStreamReader(c.getInputStream()));
            StringBuilder buf=new StringBuilder();
            String line=null;
            while ((line=reader.readLine()) != null) {
                buf.append(line + "\n");
            }
            return(buf.toString());
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private String postContent(String path, String data) {
        BufferedReader reader=null;
        try
        {
            URL url=new URL("http://192.168.1.38:58368/api/values/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestMethod("POST");




            JSONObject postDataParams = new JSONObject();
            postDataParams.put("data","data2");

            DataOutputStream localDataOutputStream = new DataOutputStream(conn.getOutputStream());
            //localDataOutputStream.writeBytes(postDataParams.toString());
            localDataOutputStream.writeBytes(new String(String.format("'" + postDataParams.toString() +"'").getBytes("UTF-8"), "ISO-8859-1"));
            localDataOutputStream.flush();
            localDataOutputStream.close();

            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader in=new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line;


                while ((line=in.readLine()) != null) {

                    sb.append(line);

                }

                in.close();
                return sb.toString();


            }
            else
            {
                return "false: " + responseCode;
            }

        }
        catch (Exception e)
        {
            return e.getMessage();
        }


    }



    private String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }


    private String getQuery(List<Pair<String,String>> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Pair<String,String> pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.first, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.second, "UTF-8"));
        }

        return result.toString();
    }
}

