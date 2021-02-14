package com.example.tenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class viewQuestions extends AppCompatActivity {
    RecyclerView recyclerView;
    String topic;

    Bundle extras;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_questions);
        extras =getIntent().getExtras();
        id= extras.getString("id");
        topic= extras.getString("topic");


        Toast.makeText(viewQuestions.this,"Success "+"\n"+id+"\n"+topic,Toast.LENGTH_SHORT).show();



        loadQuestions();



    }

    private void loadQuestions() {

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url="https://egematx.000webhostapp.com/requestQuestions.php";

        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                        JSONArray arr=new JSONArray(response);
                    String imageIdsx[]=new String[arr.length()];
                    String whoSentx[]=new String[arr.length()];
                    String imageUrlx[]=new String[arr.length()];
                    String solvedx[]=new String[arr.length()];

                        for (int i=0; i< arr.length();i++)
                        {
                            JSONArray JO= (JSONArray) arr.get(i);
                            imageIdsx[i]= (String) JO.get(0);
                            imageUrlx[i]=(String)JO.get(1);
                            whoSentx[i]=(String)JO.get(2);
                            solvedx[i]=(String)JO.get(3);

                        }
                        Log.d("karpuz", " aa"+arr.length());

                    // find recyclerview
                    recyclerView = findViewById(R.id.recyclerView);

                    // create recyclerview adapter
                    MyAdapter myAdapter= new MyAdapter(viewQuestions.this,imageIdsx, whoSentx, solvedx, imageUrlx);
                    recyclerView.setAdapter(myAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(viewQuestions.this));



                }catch (JSONException e)
                {
                    Toast.makeText(viewQuestions.this,"Error="+e.toString(),Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("topic",topic);
                params.put("id",id);
                
                return params;
            }
        };
    requestQueue.add(stringRequest);


    }

}