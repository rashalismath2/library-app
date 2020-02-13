package com.ismathlifehacks.library;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.ismathlifehacks.library.Entity.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 7;
    private GoogleSignInClient mGoogleSignInClient;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        SignInButton signInButton = findViewById(R.id.btn_google_signin);

        requestQueue=Volley.newRequestQueue(this);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Intent home=new Intent(this,HomeActivity.class);
            startActivity(home);
            finish();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("err", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    public void startSignupActivity(View view) {
        Intent signupactivity=new Intent(this,SignupActivity.class);
        startActivity(signupactivity);
    }

    public void userLogin(View view) {
        TextView email =findViewById(R.id.textEmailAddress);
        TextView password =findViewById(R.id.textPassword);

            login(email.getText().toString(),password.getText().toString(),this);

    }

    //login request
    public void login(final String email, final String password,final Context context){
        final String[] token = new String[1];
        String url="http://192.168.8.101:1010/auth/login";

        StringRequest req=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    User user=new User();
                    user.setEmail(email);
                    JSONObject res=new JSONObject(response);
                    user.setApi_token(res.getString("api_token"));
                    Intent home=new Intent(context,HomeActivity.class);
                    home.putExtra("user",user);
                    startActivity(home);
                    finish();

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
                params.put("email",email);
                params.put("password",password);

                return  params;
            }
        };
        requestQueue.add(req);

    }


}
