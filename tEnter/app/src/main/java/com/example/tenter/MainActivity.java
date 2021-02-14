package com.example.tenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText uname;
    private EditText pass;
    private Button button;
    private static String URL_LGN;
    String id;
    private String topic;
    private String fbasekey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uname =findViewById(R.id.editText);
         pass =findViewById(R.id.editText2);
        button= findViewById(R.id.button);
        URL_LGN="https://egematx.000webhostapp.com/login.php";





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (checkent()){
                   String username =uname.getText().toString().trim();
                   String password =pass.getText().toString().trim();
                   Login(username, password);
                }
            }
        });

        Toast.makeText(MainActivity.this,"x ",Toast.LENGTH_LONG).show();
    }





private boolean checkent(){
    Boolean result= false;
    String username= uname.getText().toString().trim();
    String password= pass.getText().toString().trim();

    if(username.isEmpty() || password.isEmpty())
        Toast.makeText(this,"No valid entry.",Toast.LENGTH_SHORT).show();
    else{
        result=true;}
return result;

}
private void Login(final String username, final String password){

        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL_LGN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")){
                                    String name = jsonObject.getString("username").trim();
                                    String password= jsonObject.getString("password").trim();
                                    id= jsonObject.getString("id").trim();
                                    topic= jsonObject.getString("topic").trim();
                                    fbasekey= jsonObject.getString("fbasekey").trim();
                                    if (topic.equals("student")) {
                                    Intent mainscreen = new Intent(MainActivity.this, enter_main.class);
                                    mainscreen.putExtra("id",id);
                                    mainscreen.putExtra("topic",topic);
                                    startActivity(mainscreen);
                                }
                                else{
                                    Intent mainteacher = new Intent(MainActivity.this, enter_main_teacher.class);
                                    mainteacher.putExtra("topic",topic);
                                    startActivity(mainteacher);
                                }
                            }
                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,"Error="+e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                params.put("fbasekey",MyProperties.getInstance().fbasekey);
                return params;
            }
        };
    RequestQueue requestQueue= Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);
}
}
