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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ViewListDetailsActivity extends AppCompatActivity {

    private EditText etTitle,etDesc;
    private Button btnUpdate, btnDelete;
    private AsyncHttpClient client;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_details);

        etTitle = findViewById(R.id.etTitle);
        etDesc = findViewById(R.id.etDesc);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        Intent intentReceived = getIntent();
        id = intentReceived.getIntExtra("id",0);
        client = new AsyncHttpClient();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.put("title",etTitle.getText().toString());
                params.put("description",etDesc.getText().toString());
                params.put("id",id);
                String url = "http://10.0.2.2/PDphp/updateContact.php";
                client.post(url,params,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try{
                            String message = response.getString("message");
                            Toast.makeText(ViewListDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.0.2.2/PDphp/deleteContact.php";
                RequestParams params = new RequestParams();
                params.put("id",id);
                client.post(url,params,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try{
                            String message = response.getString("message");
                            Toast.makeText(ViewListDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("id",id);
        client.post("http://10.0.2.2/PDphp/getContactDetails.php?id=" + Integer.toString(id),params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try{
                    String title = response.getString("title");
                    String desc = response.getString("description");
                    etDesc.setText(desc);
                    etTitle.setText(title);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
