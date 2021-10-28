package com.example.recycler;

import static com.example.recycler.RESTHelperClass.getResponseFromHttpUrl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView list;
    private ListAdapter mAdapter;
    private List<SHItems> listSHItems;
    private Button btnSwap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.rv_list);
        btnSwap = findViewById(R.id.swap);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true); // LinearLayout über ganze recyclerview
        listSHItems = new LinkedList<>();

      /*  listSHItems.add(new SHItems("test", "test", R.color.purple_200));
        listSHItems.add(new SHItems("test2", "test2", R.color.design_default_color_error));*/

        btnSwap.setOnClickListener(v -> {
            this.loadWebResult();
        });

        mAdapter = new ListAdapter(listSHItems);
        list.setAdapter(mAdapter);

        loadWebResult();
    }

    private void loadWebResult() {

        Networkrunnable runnable = new Networkrunnable("http://fhtw-building-control.technikum-wien.at:8080/rest/items");
        new Thread(runnable).start();
    }

    class Networkrunnable implements Runnable {
        URL url;

        public Networkrunnable(String url) {
            try {
                this.url = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            listSHItems.clear();
            try {
                JSONArray root = new JSONArray(getResponseFromHttpUrl(url));
                for(int i = 0; i < root.length(); i++){
                    JSONObject entry = root.getJSONObject(i);
                    if(!entry.getString("state").equals("NULL")){
                        if(entry.getString("type").equals("Switch")) {
                            listSHItems.add(new SHItems(entry));
                        }
                    }
                }
                //Holt sich manfred
                Handler mainHandler = new Handler(Looper.getMainLooper()); //Java concurrent weil wenn main activity stürzt sonst ab
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.swapData(listSHItems);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}