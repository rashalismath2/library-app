package com.ismathlifehacks.library;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ismathlifehacks.library.Entity.User;
import com.ismathlifehacks.library.ViewModel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new AuthCheck().execute();
    }




    public void startSignup(View view) {
        Intent signupactivity=new Intent(this,SignupActivity.class);
        startActivity(signupactivity);
    }
    public void startloginActivity(View view) {
        Intent loginActivity=new Intent(this,LoginActivity.class);
        startActivity(loginActivity);
    }


    public class AuthCheck extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            authCheck();
            return null;
        }
    }
    //Check for authentication
    public void authCheck(){
        UserViewModel user=ViewModelProviders.of(this).get(UserViewModel.class);
        User authUser=user.getUser();

        if(authUser!=null){
            Intent mainActivity=new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(mainActivity);
        }

    }

}
