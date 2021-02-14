package com.example.tenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class enter_main_teacher extends AppCompatActivity {

    Bundle extras;
    String topic;
    TextView topictext;
    Button teacherquestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_main_teacher);

        extras =getIntent().getExtras();
        teacherquestions=findViewById(R.id.teacherquestions);
        topic= extras.getString("topic");
        topictext=findViewById(R.id.teachertopic);
        topictext.setText(topic);

        MyProperties.getInstance().cachetopic=topic;
        MyProperties.getInstance().selectedtopic=topic;


        Toast.makeText(enter_main_teacher.this,"Success "+topic,Toast.LENGTH_SHORT).show();

        teacherquestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewQuestions= new Intent(enter_main_teacher.this, com.example.tenter.viewQuestions_teacher.class);
                viewQuestions.putExtra("topic",topic);
                startActivity(viewQuestions);
            }
        });
    }
}