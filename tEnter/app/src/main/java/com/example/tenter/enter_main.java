package com.example.tenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class enter_main extends AppCompatActivity {

    private Button soruyukle;
    private Button sorugor;
    String topics[]={"mathematics","physics","chemistry"};
    Spinner spinner;
    int topicId;
    Bundle extras;
    String id;
    String topic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_main);
        extras =getIntent().getExtras();
        id= extras.getString("id");
        topic=extras.getString("topic");
        MyProperties.getInstance().cachetopic=topic;




        soruyukle = findViewById(R.id.button4);
        sorugor = findViewById(R.id.button5);
        spinner=findViewById(R.id.spinner);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                topicId = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        soruyukle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yukle = new Intent(enter_main.this,questions.class);
                yukle.putExtra("id",id);
                startActivity(yukle);
            }
        });

        sorugor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewQuestions= new Intent(enter_main.this, com.example.tenter.viewQuestions.class);
                viewQuestions.putExtra("topic",topics[topicId]);
                viewQuestions.putExtra("id",id);
                MyProperties.getInstance().selectedtopic=topics[topicId];
                startActivity(viewQuestions);
            }
        });
    }



}
