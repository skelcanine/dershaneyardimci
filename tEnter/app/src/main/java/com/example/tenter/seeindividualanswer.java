package com.example.tenter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class seeindividualanswer extends AppCompatActivity {

    ImageView answerImage;
    TextView answerId,senderId,questionId;
    String Url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeindividualanswer);

        answerImage=findViewById(R.id.ans_answerImage);
        answerId=findViewById(R.id.ans_answerId);
        senderId=findViewById(R.id.ans_senderId);
        questionId=findViewById(R.id.ans_questionId);

        answerId.setText("Cevap: "+getIntent().getStringExtra("answerId"));
        senderId.setText("Gonderen: "+getIntent().getStringExtra("senderId"));
        questionId.setText("Soru: "+getIntent().getStringExtra("imageId"));
        Url=getIntent().getStringExtra("imageUrl");
        Picasso.get().load(Url).into(answerImage);









        answerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(seeindividualanswer.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                PhotoView photoView = mView.findViewById(R.id.imageViewx);
                Picasso.get().load(Url).into(photoView);
                mBuilder.setView(mView);
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }
        });
    }
}