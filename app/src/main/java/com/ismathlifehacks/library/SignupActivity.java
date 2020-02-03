package com.ismathlifehacks.library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


    }

    public void startloginActivity(View view) {
        Intent loginActivity=new Intent(this,LoginActivity.class);
        startActivity(loginActivity);
    }
}
