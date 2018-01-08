package com.learn.hp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Vector;

/**
 * Created by hp on 07-09-2017.
 */

public class ReviewDb {
    public static final String KEY_MOVIE_ID = "movie_id";
    public static final String KEY_MOVIE_NAME = "movie_name";
    public static final String KEY_REVIEW_ID = "review_id";
    public static final String KEY_REVIEW = "review";
    public static final String KEY_LIKE_COUNT = "count";
    public static final String KEY_USER_ID = "user_id";



    private static final String DATABASE_NAME="Yog";
    private static final String DATABASE_TABLE="Review";
    private static final int DATABASE_VERSION=1;

    private DbHelper ourHelper;
    private Context ourContext;
    private SQLiteDatabase ourDatabase;




    private static class DbHelper extends SQLiteOpenHelper {


        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table "+DATABASE_TABLE+" ("
                    +KEY_REVIEW_ID+" integer primary key autoincrement, "
                    +KEY_REVIEW+" text not null,"
                    +KEY_MOVIE_NAME+" text not null,"
                    +KEY_MOVIE_ID+" integer not null);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists "+DATABASE_TABLE);
            onCreate(db);
        }

    }
    public ReviewDb(Context c)
    {
        ourContext=c;
    }

    public ReviewDb open() throws SQLException
    {
        ourHelper =new DbHelper(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();
        return this;
    }
    public ReviewDb openReadable()throws SQLException{
        ourHelper =new DbHelper(ourContext);
        ourDatabase=ourHelper.getReadableDatabase();
        return this;
    }

    public long createEntry(MovieDetails details, String rev) {
        ContentValues cv=new ContentValues();
        cv.put(KEY_MOVIE_ID,details.getId());
        cv.put(KEY_MOVIE_NAME,details.getOriginal_title());
        cv.put(KEY_REVIEW,rev);
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
    public String readData(String mid)
    {
        String[] column =new String[]{KEY_MOVIE_NAME,KEY_REVIEW,KEY_REVIEW_ID,KEY_MOVIE_ID};
        String qr=KEY_MOVIE_ID+" = ?";
        String value_qr[]={mid};
        Cursor cu= ourDatabase.query(DATABASE_TABLE,column,qr,value_qr,null,null,null);
        String result="";
        int imname=cu.getColumnIndex(KEY_MOVIE_NAME);
        int imreview=cu.getColumnIndex(KEY_REVIEW);
        int imrevid=cu.getColumnIndex(KEY_REVIEW_ID);
        int imid=cu.getColumnIndex(KEY_MOVIE_ID);

        for (cu.moveToFirst();!cu.isAfterLast();cu.moveToNext()){
            //if(mid.contains(cu.getString(imid))){
            result=result+cu.getString(imname)+" "+cu.getString(imrevid)+" "+cu.getString(imreview)+" "+"\n";
        }
        return result;
    }

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
           // vect.add(temp);*/
        }

        return arr;
    }

    public void close()
    {

        ourHelper.close();
    }


}
