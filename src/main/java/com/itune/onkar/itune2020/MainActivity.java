package com.itune.onkar.itune2020;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    SearchView search;
    JSONObject jo;
    JsonObjectRequest Jreq;
    JSONArray ja;
    private RequestQueue req;
    ListView lst;
    ArrayAdapter<String> adapter;
    ArrayList<String> l = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (SearchView) findViewById(R.id.sea);
        Button bn= (Button)findViewById(R.id.btn);
         lst=(ListView)findViewById(R.id.listitm);
        req= Volley.newRequestQueue(this);
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();




            }
        });
       Button reset= (Button)findViewById(R.id.rst);
        lst=(ListView)findViewById(R.id.listitm);
        req= Volley.newRequestQueue(this);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reset();
            }
        });


    }

    private void Reset() {
        l.removeAll(l);
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1,l);
        lst.setAdapter(adapter);
    }

    private void jsonParse() {


        String b2=search.getQuery().toString();

        String url="https://itunes.apple.com/search?term="+b2;
         Jreq= new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                             ja = response.getJSONArray("results");

                            for(int i=0;i<ja.length();i++){
                                 jo =ja.getJSONObject(i);
                                String title =jo.getString("trackName");
                                l.add(title);

                            }

                        }catch (JSONException e){
                            e.printStackTrace();

                        }}
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1,l);
        lst.setAdapter(adapter);
        req.add(Jreq);
    }
}
