package com.learn.hp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by hp on 05-10-2017.
 */

public class WatchList extends AppCompatActivity {

    ListView listView;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        WatchlistDb w = new WatchlistDb(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.watchlist_xml);
        try{
            user=(User) getIntent().getExtras().getSerializable("User");
            w.openReadable();
     //   String[] items={"a","b"};
            String[] items;
            items= w.readData(user);
            Log.d("error",items[0]);
            listView=(ListView)findViewById(R.id.watchlist_id);
            ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
            listView.setAdapter(adapt);
            w.close();}
        catch (Exception e){
        Log.d("errorws ",""+e);
        }

    }
}
