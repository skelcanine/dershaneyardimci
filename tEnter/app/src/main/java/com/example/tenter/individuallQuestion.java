package com.example.tenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

public class individuallQuestion extends AppCompatActivity {

    private static  final  int STORAGE_PERMISSION_CODE=4655;
    private  int PICK_IMAGE_RESULT=1;
    private Uri filepath;
    public  static  final  String UPLOAD_URL="http://egematx.000webhostapp.com/uphotoanswer.php";
    RecyclerView recyclerView;
    ImageView imageQuestion;
    TextView imageId,sender,did_choose;
    Button selectImage,send;
    String data1,data2,data3;
    String topic;
    ProgressDialog progressDialog;
    Boolean check=true;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individuall_question);

        recyclerView = findViewById(R.id.ind_recyclerView);
        imageQuestion = findViewById(R.id.ind_question);
        did_choose=findViewById(R.id.did_choose);
        imageId = findViewById(R.id.ind_questionId);
        sender = findViewById(R.id.ind_whosent);
        selectImage = findViewById(R.id.ind_chooseimage);
        send = findViewById(R.id.ind_send);
        storagePermission();
        loadAnswers();




        if (MyProperties.getInstance().cachetopic.equals("student"))
        {
            send.setVisibility(View.GONE);
            selectImage.setVisibility(View.GONE);
            did_choose.setText("");
        }else
        {
            did_choose.setText("Resim Seciniz");
        }
        topic=MyProperties.getInstance().cachetopic;
        getData();
        setData();
        imageQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(individuallQuestion.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                PhotoView photoView = mView.findViewById(R.id.imageViewx);
                Picasso.get().load(data3).into(photoView);
                mBuilder.setView(mView);
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

    }
    private void getData()
    {
        if(getIntent().hasExtra("imageId") && getIntent().hasExtra("sender") && getIntent().hasExtra("imageUrl"))
        {

            data1 = getIntent().getStringExtra("imageId");
            data2 = getIntent().getStringExtra("sender");
            data3 = getIntent().getStringExtra("imageUrl");


        }else
        {
            Toast.makeText(this,"Bilgilere Ulasilamadi.",Toast.LENGTH_SHORT).show();

        }





    }
    private void setData()
    {
        imageId.setText(data1);
        sender.setText(data2);
        Picasso.get().load(data3).into(imageQuestion);
    }
    private  void storagePermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==STORAGE_PERMISSION_CODE)
        {
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Permission granted.",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this,"Permission Denied.",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void selectImage(View view)
    {
        showFileChooser();
    }

    private void showFileChooser()
    {
        Intent intent=new Intent();
        intent.setType("image/jpeg");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_RESULT && data!=null && data.getData() != null) {

            filepath=data.getData();
            try {
                did_choose.setText("Resim Secildi");
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
            }catch (Exception ex){}
        }

    }



    public void  saveData(View view)
    {
        ImageUploadToServerFunction();

    }

    public void ImageUploadToServerFunction() {
        ByteArrayOutputStream byteArrayOutputStreamObject;
        byteArrayOutputStreamObject = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();
        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(individuallQuestion.this, "Image is Uploading", "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);
                // Dismiss the progress dialog after done uploading.
                progressDialog.dismiss();
                // Printing uploading success message coming from server on android app.
                Toast.makeText(individuallQuestion.this, string1, Toast.LENGTH_LONG).show();
                // Setting image as transparent after done uploading.

            }

            @Override
            protected String doInBackground(Void... params) {
                individuallQuestion.ImageProcessClass imageProcessClass =new  individuallQuestion.ImageProcessClass();
                HashMap<String, String> HashMapParams = new HashMap<String, String>();
                HashMapParams.put("imageid", data1);
                HashMapParams.put("senderid", data2);
                HashMapParams.put("topic", topic);
                HashMapParams.put("image_path", ConvertImage);
                String FinalData = imageProcessClass.ImageHttpRequest(UPLOAD_URL, HashMapParams);
                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    private void loadAnswers() {

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url="https://egematx.000webhostapp.com/requestAnswers.php";

        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray arr=new JSONArray(response);
                    String answerIdsx[]=new String[arr.length()];
                    String whoSentx[]=new String[arr.length()];
                    String imageUrlx[]=new String[arr.length()];
                    String questionIdx[]=new String[arr.length()];

                    for (int i=0; i< arr.length();i++)
                    {
                        JSONArray JO=(JSONArray) arr.get(i);
                        answerIdsx[i]= (String) JO.get(0);
                        imageUrlx[i]=(String)JO.get(1);
                        whoSentx[i]=(String)JO.get(2);
                        questionIdx[i]=(String)JO.get(3);

                    }
                    Log.d("karpuz", " aa"+arr.length()+" aa"+ data1 + MyProperties.getInstance().selectedtopic);

                    // find recyclerview
                    recyclerView = findViewById(R.id.ind_recyclerView);

                    // create recyclerview adapter
                    MyAdapter2 myAdapterx= new MyAdapter2(individuallQuestion.this,answerIdsx, whoSentx, questionIdx, imageUrlx);
                    recyclerView.setAdapter(myAdapterx);
                    recyclerView.setLayoutManager(new LinearLayoutManager(individuallQuestion.this));



                }catch (JSONException e)
                {
                    Toast.makeText(individuallQuestion.this,"Errorx="+e.toString(),Toast.LENGTH_SHORT).show();
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
                params.put("topic",MyProperties.getInstance().selectedtopic);
                params.put("id",data1);

                return params;
            }
        };
        requestQueue.add(stringRequest);


    }

    public class ImageProcessClass {

        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url;
                HttpURLConnection httpURLConnectionObject;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject;
                BufferedReader bufferedReaderObject;
                int RC;
                url = new URL(requestURL);
                httpURLConnectionObject = (HttpURLConnection) url.openConnection();
                httpURLConnectionObject.setReadTimeout(19000);
                httpURLConnectionObject.setConnectTimeout(19000);
                httpURLConnectionObject.setRequestMethod("POST");
                httpURLConnectionObject.setDoInput(true);
                httpURLConnectionObject.setDoOutput(true);
                OutPutStream = httpURLConnectionObject.getOutputStream();
                bufferedWriterObject = new BufferedWriter(
                        new OutputStreamWriter(OutPutStream, "UTF-8"));
                bufferedWriterObject.write(bufferedWriterDataFN(PData));
                bufferedWriterObject.flush();
                bufferedWriterObject.close();
                OutPutStream.close();
                RC = httpURLConnectionObject.getResponseCode();
                if (RC == HttpsURLConnection.HTTP_OK) {
                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));
                    stringBuilder = new StringBuilder();
                    String RC2;
                    while ((RC2 = bufferedReaderObject.readLine()) != null) {
                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {
            StringBuilder stringBuilderObject;
            stringBuilderObject = new StringBuilder();
            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilderObject.append("&");
                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));
                stringBuilderObject.append("=");
                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }
            return stringBuilderObject.toString();
        }
    }


}