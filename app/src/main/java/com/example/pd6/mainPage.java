package com.example.pd6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class mainPage extends AppCompatActivity {

    private ListView lvToDo;
    private ArrayList<ToDo> alToDo;
    private ArrayAdapter<ToDo> aaToDo;
    private AsyncHttpClient client;

    private String loginId;
    private String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        lvToDo = findViewById(R.id.listViewToDo);
        client =   new AsyncHttpClient();
        alToDo = new ArrayList<ToDo>();
        aaToDo = new ToDoAdapter(this,R.layout.todo_row,alToDo);
        lvToDo.setAdapter(aaToDo);

        Intent i = getIntent();
        loginId = i.getStringExtra("loginId");
        apiKey = i.getStringExtra("apikey");

        lvToDo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ToDo selectedDo = alToDo.get(i);

                Intent intent = new Intent(mainPage.this, ViewListDetailsActivity.class);
                intent.putExtra("id",selectedDo.getListId());
                intent.putExtra("loginId",loginId);
                intent.putExtra("apikey",apiKey);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        alToDo = new ArrayList<ToDo>();
        alToDo.clear();

        RequestParams params = new RequestParams();
        params.put("loginId",loginId);
        params.put("apikey", apiKey);

        String url = "http://10.0.2.2/PDphp/getListOfContacts.php";

        client.post(url,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObj = response.getJSONObject(i);

                        int listId = jsonObj.getInt("id");
                        String title = jsonObj.getString("title");
                        String desc = jsonObj.getString("description");

                        ToDo todo = new ToDo(listId,title,desc);
                        alToDo.add(todo);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                aaToDo = new ToDoAdapter(mainPage.this,R.layout.todo_row, alToDo);
                lvToDo.setAdapter(aaToDo);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add) {


            Intent intent = new Intent(getApplicationContext(), CreateItemActivity.class);
            intent.putExtra("loginId", loginId);
            intent.putExtra("apikey", apiKey);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
