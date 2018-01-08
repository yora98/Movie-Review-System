package com.learn.hp;

import java.io.Serializable;

/**
 * Created by hp on 17-09-2017.
 */

public class User implements Serializable {
    private int id;
    private String uid;
    private String pass;

    void setId(String temp)
    {id=Integer.parseInt(temp);
    }
    void setUid(String temp)
    {uid=temp;}
    void setpass(String temp){
        pass=temp;
    }
    String getUid(){return uid;}
    String getid(){return ""+id;}
    String getPass(){return pass;}
}
