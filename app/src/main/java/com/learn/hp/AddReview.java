package com.learn.hp;

import android.app.Activity;
import android.app.Dialog;
import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.spec.ECField;

/**
 * Created by hp on 16-09-2017.
 */

public class AddReview extends AppCompatActivity implements View.OnClickListener{

    EditText rev;
    Button but,but2;
    TextView tt;
    String review_string;
    MovieDetails details;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_review);
        but2=(Button)findViewById(R.id.view_button);
        tt=(TextView)findViewById(R.id.view_review);
        rev=(EditText)findViewById(R.id.review_text);
        but=(Button)findViewById(R.id.submit);
        details = (MovieDetails) getIntent().getExtras().getSerializable("MOVIE_DETAILS");
        but.setOnClickListener(this);
        but2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                try{
                ReviewDb db = new ReviewDb(this);
                db.open();
                    review_string=rev.getText().toString();
                db.createEntry(details, review_string);
                Dialog d=new Dialog(this);
                d.setTitle("HEY Sorry");
                TextView tv=new TextView(this);
                tv.setText("Succesfully added");
                d.setContentView(tv);
                d.show();
                db.close();}catch (SQLException e){}
                break;
            case R.id.view_button:
                try{
                    ReviewDb db = new ReviewDb(this);
                    db.openReadable();
                //    String st=db.readData();
                  //  tt.setText(st);
                    db.close();}catch (SQLException e){}
                break;

        }
    }
}
