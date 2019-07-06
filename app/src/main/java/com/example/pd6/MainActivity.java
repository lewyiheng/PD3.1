package com.example.pd6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new AsyncHttpClient();
        etUsername = findViewById(R.id.editTextUsername);
        etPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestParams params = new RequestParams();
                params.add("username", etUsername.getText().toString());
                params.add("password", etPassword.getText().toString());

                String url = "http://10.0.2.2/PDphp/doLogin.php";

                client.post(url,params, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {
                            Boolean authenticated = response.getBoolean("authenticated");
                            if (authenticated == true){

                                String apikey = response.getString("apikey");
                                String id = response.getString("id");

                                Intent intent = new Intent(MainActivity.this,mainPage.class);
                                intent.putExtra("loginId",id);
                                intent.putExtra("apikey",apikey);

                                startActivity(intent);

                            }else{
                                Toast.makeText(MainActivity.this, "Failed to log in.", Toast.LENGTH_SHORT).show();
                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Failed to log in.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
