package com.learn.hp;

import java.io.Serializable;

/**
 * Created by hp on 17-09-2017.
 */

public class ReviewC {

    private  String writer_id;
    private String Movie_name;
    private String Movie_id;
    private String review;
    private String reviewid;

    void setReview(String temp){review=temp;}
    void setWriterId (String temp){writer_id=temp;}
    void setMovie_name (String  temp){Movie_name=temp;}
    void setMovie_id (String temp){Movie_id=temp;}
    void setReviewid(String id){reviewid=""+id;}
    String getReview(){return review;}
    String getReviewid(){return reviewid;}
    String getMovie_name(){return Movie_name;}
    String getMovie_id(){return Movie_id;}
    String getWriter_id(){return writer_id;}

}
