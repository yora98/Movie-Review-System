package com.learn.hp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.learn.hp.R;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private Button watchlistbut;
    Intent intent;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        watchlistbut=(Button)findViewById(R.id.butwatch);
//Executing AsyncTask, passing api as parameter
        watchlistbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // intent = new Intent(this, WatchList.class);
                //intent.putExtra("MOVIE_DETAILS",(MovieDetails)details );z
               //  this.startActivity(intent);
                user=(User)getIntent().getExtras().getSerializable("User");
                Log.d("MainUser",user.getUid());
                intent = new Intent(MainActivity.this, WatchList.class);
                intent.putExtra("User",user);
                MainActivity.this.startActivity(intent);
            }
        });
        new CheckConnectionStatus().execute("https://api.themoviedb.org/3/movie/popular?api_key=8476a7ab80ad76f0936744df0430e67c&language=en-US&page=1");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    //This method is invoked whenever we click over any item of list
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //Moving to MovieDetailsActivity from MainActivity. Sending the MovieDetails object from one activity to another activity
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("MOVIE_DETAILS", (MovieDetails)parent.getItemAtPosition(position));
        user=(User)getIntent().getExtras().getSerializable("User");
        Log.d("MainUser",user.getUid());
        intent.putExtra("User",user);
        startActivity(intent);

    }


    //AsyncTask to process network request
    class CheckConnectionStatus extends AsyncTask<String, Void, String> {
        //This method will run on UIThread and it will execute before doInBackground
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        //This method will run on background thread and after completion it will return result to onPostExecute
        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
//As we are passing just one parameter to AsyncTask, so used param[0] to get value at 0th position that is URL
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//Getting inputstream from connection, that is response which we got from server
                InputStream inputStream = urlConnection.getInputStream();
//Reading the response
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String s = bufferedReader.readLine();
                bufferedReader.close();
//Returning the response message to onPostExecute method
                return s;
            } catch (IOException e) {
                Log.e("Error: ", e.getMessage(), e);
            }
            return null;
        }
        //This method runs on UIThread and it will execute when doInBackground is completed
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonObject = null;
            try {

//Parent JSON Object. Json object start at { and end at }
                jsonObject = new JSONObject(s);

                ArrayList<MovieDetails> movieList = new ArrayList<>();

//JSON Array of parent JSON object. Json array starts from [ and end at ]
                JSONArray jsonArray = jsonObject.getJSONArray("results");

//Reading JSON object inside Json arr
// ay
                for (int i =0; i<jsonArray.length();i++)
                {

//Reading JSON object at 'i'th position of JSON Array
                    JSONObject object = jsonArray.getJSONObject(i);
                    MovieDetails movieDetails = new MovieDetails();
                    movieDetails.setOriginal_title(object.getString("original_title"));
                    movieDetails.setVote_average(object.getDouble("vote_average"));
                    movieDetails.setOverview(object.getString("overview"));
                    movieDetails.setRelease_date(object.getString("release_date"));
                    movieDetails.setPoster_path(object.getString("poster_path"));
                    movieDetails.setId(Integer.parseInt(object.getString("id")));
                    movieList.add(movieDetails);

                }

                //Creating custom array adapter instance and setting context of MainActivity, List item layout file and movie list.
                MovieArrayAdapter movieArrayAdapter = new MovieArrayAdapter(MainActivity.this,R.layout.movie_list,movieList);

                //Setting adapter to listview
                listView.setAdapter(movieArrayAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}