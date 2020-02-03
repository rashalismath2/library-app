package com.ismathlifehacks.library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startSignup(View view) {
        Intent signupactivity=new Intent(this,SignupActivity.class);
        startActivity(signupactivity);
    }
    public void startloginActivity(View view) {
        Intent loginActivity=new Intent(this,LoginActivity.class);
        startActivity(loginActivity);
    }
}
