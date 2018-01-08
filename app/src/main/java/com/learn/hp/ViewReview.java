package com.learn.hp;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
/**
 * Created by hp on 16-09-2017.
 */

public class ViewReview extends AppCompatActivity {
    String st;
    MovieDetails details;
    TextView rid,mname,mreview;
    ReviewC arr[];
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_review);
        //initiate();
        details = (MovieDetails) getIntent().getExtras().getSerializable("MOVIE_DETAILS");

        try {
            ReviewDb db = new ReviewDb(ViewReview.this);
            db.openReadable();
             arr = db.getList(details.getId());
            Log.d("arr",arr[0].getReview());
            ListAdapter adap=new ReviewArrayAdapter(this,arr);
            ListView list =(ListView)findViewById(R.id.list_view1);
            list.setAdapter(adap);
            db.close();

        }catch (Exception e){
            System.out.print(e);
            Log.d("error",""+e);
        }
      /*  try {
            ReviewDb db = new ReviewDb(ViewReview.this);
            db.openReadable();
            st = db.readData(details.getId());
            db.close();
            mreview.setText(st);
            mname.setText(details.getOriginal_title());
        }catch (SQLException e){
            Dialog d=new Dialog(this);
            d.setTitle("HEY Sorry");
            TextView tv=new TextView(this);
            tv.setText("Sorrrrrrrrrrrry add");
            d.setContentView(tv);
            d.show();
        }*/

    }

    void initiate(){
        rid=(TextView)findViewById(R.id.textView3);
        mname=(TextView)findViewById(R.id.textView4);
        mreview=(TextView)findViewById(R.id.textView5);

    }
}
