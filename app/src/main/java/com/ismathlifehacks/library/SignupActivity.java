package com.ismathlifehacks.library;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ismathlifehacks.library.Entity.User;
import com.ismathlifehacks.library.ViewModel.UserViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class SignupActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        requestQueue=Volley.newRequestQueue(this);

    }

    public void signup(final User user, final String password,final Context context){

       Context contex=context;
        String url="http://192.168.8.101:1010/auth/signup";

        StringRequest req=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                login(user,password);

                Intent home=new Intent(context,HomeActivity.class);
                home.putExtra("user",user);
                startActivity(home);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("resErr", String.valueOf(error));
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String,String>();
                params.put("email",user.getEmail());
                params.put("first_name",user.getFirst_name());
                params.put("last_name",user.getLast_name());
                params.put("password",password);

                return  params;
            }
        };
        requestQueue.add(req);

    }

    public void startloginActivity(View view) {
        Intent loginActivity=new Intent(this,LoginActivity.class);
        startActivity(loginActivity);
    }


    //signup request
    public void userSignup(View view) {
        TextView email=findViewById(R.id.txtemail);
        TextView first_name=findViewById(R.id.txtfirst_name);
        TextView last_name=findViewById(R.id.txtlast_name);
        TextView password=findViewById(R.id.txtpassword);

        User user=new User();
        user.setEmail(email.getText().toString());
        user.setFirst_name(first_name.getText().toString());
        user.setLast_name(last_name.getText().toString());

        signup(user,password.getText().toString(),this);
    }

        //login request
    public void login(final User user,final String password){
        final String[] token = new String[1];
        String url="http://192.168.8.101:1010/auth/login";

        StringRequest req=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res=new JSONObject(response);
                    user.setApi_token(res.getString("api_token"));

                } catch (JSONException e) {
                    Log.d("resErr", String.valueOf(e));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("resErr", String.valueOf(error));
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String,String>();
                params.put("email",user.getEmail());
                params.put("password",password);

                return  params;
            }
        };
        requestQueue.add(req);

    }
}
