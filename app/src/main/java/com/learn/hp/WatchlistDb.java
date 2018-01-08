package com.learn.hp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hp on 05-10-2017.
 */

public class WatchlistDb {
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_WATCHLIST_MOVIE = "movie_name";



    private static final String DATABASE_NAME="Watchlist";
    private static final String DATABASE_TABLE="moviewatchlist";
    private static final int DATABASE_VERSION=1;

    private WatchlistDb.DbHelperWatchlist ourHelper;
    private Context ourContext;
    private SQLiteDatabase ourDatabase;




    private static class DbHelperWatchlist extends SQLiteOpenHelper {


        public DbHelperWatchlist(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table "+DATABASE_TABLE+" ("
                    +KEY_USER_NAME+" text not null, "
                    +KEY_WATCHLIST_MOVIE+" text not null "
                    +");"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists "+DATABASE_TABLE);
            onCreate(db);
        }

    }
    public WatchlistDb(Context c)
    {
        ourContext=c;
    }

    public WatchlistDb open() throws SQLException
    {
        ourHelper =new WatchlistDb.DbHelperWatchlist(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();
        return this;
    }
    public WatchlistDb openReadable()throws SQLException{
        ourHelper =new WatchlistDb.DbHelperWatchlist(ourContext);
        ourDatabase=ourHelper.getReadableDatabase();
        return this;
    }

    public long createEntry(MovieDetails details,User temp) {
        ContentValues cv=new ContentValues();
        cv.put(KEY_USER_NAME,temp.getUid());
        Log.d("Details",temp.getUid());
        cv.put(KEY_WATCHLIST_MOVIE,details.getOriginal_title());

        // cv.put(KEY_USER_ID,);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }
    /*  public void delete(String name) {
          String[] column =new String[]{KEY_ROWID,KEY_NAME,KEY_PASS};
          Cursor cu= ourDatabase.query(DATABASE_TABLE,column,null,null,null,null,null);
          int irow=cu.getColumnIndex(KEY_ROWID);
          int iname=cu.getColumnIndex(KEY_NAME);
          int ipass=cu.getColumnIndex(KEY_PASS);
          int id;
          for (cu.moveToFirst();!cu.isAfterLast();cu.moveToNext()){
              if(cu.getString(iname).equals(name)){
                  id=Integer.parseInt(cu.getString(irow));
                  ourDatabase.delete(DATABASE_TABLE,KEY_ROWID+"="+id,null);
                  break;
              }
          }

      }

  */
    public String[] readData(User temp) throws Exception
    {
        String[] column =new String[]{KEY_USER_NAME,KEY_WATCHLIST_MOVIE};
        String qr=KEY_USER_NAME+" = ?";
        String value_qr[]={temp.getUid()};
        Cursor cu= ourDatabase.query(DATABASE_TABLE,column,qr,value_qr,null,null,null);
        String[] result=new String[cu.getCount()];
        int iusername=cu.getColumnIndex(KEY_USER_NAME);
        int imovie=cu.getColumnIndex(KEY_WATCHLIST_MOVIE);
        int i=0;

        for (cu.moveToFirst();!cu.isAfterLast();cu.moveToNext()){
            //if(mid.contains(cu.getString(imid))){

            result[i]=cu.getString(imovie);
            i++;

        }
        return result;
    }
/*
    public ReviewC[] getList(String mid){
        String[] column =new String[]{KEY_MOVIE_NAME,KEY_REVIEW,KEY_REVIEW_ID,KEY_MOVIE_ID};
        String qr=KEY_MOVIE_ID+" = ?";
        String value_qr[]={mid};
        Cursor cu= ourDatabase.query(DATABASE_TABLE,column,qr,value_qr,null,null,null);
        String result="";
        int imname=cu.getColumnIndex(KEY_MOVIE_NAME);
        int imreview=cu.getColumnIndex(KEY_REVIEW);
        int imrevid=cu.getColumnIndex(KEY_REVIEW_ID);
        int imid=cu.getColumnIndex(KEY_MOVIE_ID);
        //Vector<ReviewC> vect=new Vector<ReviewC>();
        ReviewC[] arr=new ReviewC[cu.getCount()];

        for (cu.moveToFirst();!cu.isAfterLast();cu.moveToNext()){
            //  ReviewC temp=
            arr[cu.getPosition()]=new ReviewC();;
            arr[cu.getPosition()].setMovie_id(cu.getString(imid));
            arr[cu.getPosition()].setMovie_name(cu.getString(imname));
            arr[cu.getPosition()].setReview(cu.getString(imreview));
            arr[cu.getPosition()].setReviewid(cu.getString(imrevid));
            // vect.add(temp);
        }

        return arr;
    }*/

    public void close()
    {

        ourHelper.close();
    }

}
