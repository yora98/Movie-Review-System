package com.learn.hp;

import android.app.Dialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;



public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView image;
    private Intent intent;
    private TextView title, date, rating, overview,idmov;
    private Button addd,vieww,addwatch;
    MovieDetails details;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        image = (ImageView) findViewById(R.id.poster);
        idmov=(TextView)findViewById(R.id.movieid);
        title = (TextView) findViewById(R.id.title);
        addd=(Button)findViewById(R.id.addreview);
        vieww=(Button) findViewById(R.id.viewreview);
        date = (TextView)findViewById(R.id.date);
        addwatch=(Button)findViewById(R.id.addtowatchlist);
        addd.setOnClickListener(this);
        vieww.setOnClickListener(this);
        addwatch.setOnClickListener(this);
        overview = (TextView) findViewById(R.id.overview);

        //Getting the value from bundle, means the value which we had during switching to this activity from main activity
        details = (MovieDetails) getIntent().getExtras().getSerializable("MOVIE_DETAILS");
        user = (User) getIntent().getExtras().getSerializable("User");
        if(details !=null)
        {

            Glide.with(this).load("https://image.tmdb.org/t/p/w500/"+ details.getPoster_path()).into(image);
            idmov.setText(details.getId());
            title.setText(details.getOriginal_title());
            date.setText(details.getRelease_date());
            overview.setText(details.getOverview());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addreview:
                intent = new Intent(MovieDetailActivity.this, AddReview.class);
                intent.putExtra("MOVIE_DETAILS", (MovieDetails) details);
                MovieDetailActivity.this.startActivity(intent);
                break;

            case R.id.viewreview:
                Intent intent1 = new Intent(MovieDetailActivity.this, ViewReview.class);
                intent1.putExtra("MOVIE_DETAILS", (MovieDetails) details);
                MovieDetailActivity.this.startActivity(intent1);
                break;
            case R.id.addtowatchlist:

                try {
                    WatchlistDb db = new WatchlistDb(this);
                    db.open();
                    Log.d("User",user.getUid());
                    Log.d("Details",details.getOriginal_title());
                    db.createEntry(details, user);
                    Dialog d=new Dialog(this);
                    d.setTitle("hey");
                    TextView tv=new TextView(this);
                    tv.setText("Succesfully added");
                    d.setContentView(tv);
                    d.show();
                    db.close();
                } catch (SQLException e) {
                }
                break;
        }
    }
}
