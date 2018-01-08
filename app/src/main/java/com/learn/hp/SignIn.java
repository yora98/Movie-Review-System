package com.learn.hp;

import android.app.Dialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by hp on 04-10-2017.
 */

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    private EditText userid,pass;
    private Button submit,signup;
    Intent intent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        initiate();
        signup.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    private void initiate() {
        userid=(EditText) findViewById(R.id.sign_uid);
        pass=(EditText) findViewById(R.id.sign_pass);
        signup=(Button)findViewById(R.id.sign_up_id);
        submit=(Button)findViewById(R.id.sign_submit);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_up_id:
                try{
                    String username,userpass;
                    User newuser=new User();
                    UserDb db = new UserDb(this);
                    db.open();
                    username=userid.getText().toString();
                    newuser.setUid(username);
                    newuser.setpass(pass.getText().toString());
                    db.createEntry(newuser);
                    Dialog d=new Dialog(this);
                    d.setTitle("HEY Sorry");
                    TextView tv=new TextView(this);
                    Log.d("username",username);
                    tv.setText("Succesfully added");
                    d.setContentView(tv);
                    d.show();
                    db.close();}catch (SQLException e){}
                break;
            case R.id.sign_submit:
                try{
                    String username,userpass;
                    User newuser=new User();
                    UserDb db = new UserDb(this);
                    db.open();
                    username=userid.getText().toString();
                    newuser.setUid(username);
                    newuser.setpass(pass.getText().toString());
                    userpass=db.readData(newuser);
                            if(userpass.compareTo(pass.getText().toString())==0){
                                intent = new Intent(this, MainActivity.class);
                                intent.putExtra("User",newuser);
                                this.startActivity(intent);
                            }
                            else{
                                Dialog d=new Dialog(this);
                                d.setTitle("HEY Sorry");
                                TextView tv=new TextView(this);
                                Log.d("username",username);
                                tv.setText("Sorry Wrong password or username");
                                d.setContentView(tv);
                                d.show();
                            }
                                db.close();}catch (SQLException e){}
                break;
        }
    }
}
