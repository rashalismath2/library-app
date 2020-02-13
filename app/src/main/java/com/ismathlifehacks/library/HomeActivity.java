package com.ismathlifehacks.library;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ismathlifehacks.library.Entity.User;
import com.ismathlifehacks.library.Model.Author;
import com.ismathlifehacks.library.Model.Book;
import com.ismathlifehacks.library.ViewModel.UserViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private GoogleSignInClient mGoogleSignInClient;
    private RequestQueue requestQueue;
    private User user;
    private List<Book> books;
    RecyclerView newBooksRecyclerView;
    NewBooksRecyclerViewAdapter newItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        drawer=findViewById(R.id.my_drawer);
        NavigationView myNav=findViewById(R.id.my_nav);
        myNav.setNavigationItemSelectedListener(this);

        Intent i=getIntent();
        user= (User) i.getSerializableExtra("user");

        if(user!=null){
            new saveUser(user).execute();
        }
        requestQueue=Volley.newRequestQueue(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        if(getGoogleInfos()!=null){
            new saveUser(getGoogleInfos()).execute();
        }

        CreateNewItemRecyclerView();

        CreateAuthorItemRecyclerView();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public User getGoogleInfos(){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();

            user=new User();
            user.setEmail(personEmail);
            user.setFirst_name(personGivenName);
            user.setLast_name(personName);
            user.setImg(personPhoto.toString());
//            user.setApi_token(acct.getIdToken());
            signup(user,"password",this);
            login(user,"password");
            return  user;
        }
        else return null;
    }
    public void signup(final User user, final String password,final Context context){

        Context contex=context;
        String url="http://192.168.8.101:1010/auth/signup";

        StringRequest req=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                if(user.getImg()!=null){params.put("img",user.getImg());}
                else{
                    params.put("img","");
                }

                return  params;
            }
        };
        requestQueue.add(req);

    }

    //login request
    public void login(final User user,final String password){
        String url="http://192.168.8.101:1010/auth/login";

        StringRequest req=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res=new JSONObject(response);

                    user.setApi_token(res.getString("api_token"));
                    new CreateNewBooksItems(user).execute();

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



    //save user to lite database
    public void saveUserToDb(User user){
        UserViewModel userViewModel=ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.deleteAllUsers();
        userViewModel.insert(user);
    }
    //delete user from lite database
    public void deleteUserToLogout(){
        UserViewModel userViewModel=ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.deleteAllUsers();
    }

    public void openDrawer(View view) {
        drawer.openDrawer(Gravity.START);
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(Gravity.START);
        }else{
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.btn_logout:
                drawer.closeDrawer(Gravity.START);

                    mGoogleSignInClient.signOut()
                            .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    callmain();
                                }
                            });

                break;

            case R.id.btnLocation:
                drawer.closeDrawer(Gravity.START);
                Intent map=new Intent(this,MapsActivity.class);
                startActivity(map);

                break;
        }

        return true;
    }

    public void callmain(){
        deleteUserToLogout();
        Intent login=new Intent(this,LoginActivity.class);
        startActivity(login);
        finish();
    }

    public class saveUser extends AsyncTask<Void,Void,Void> {
        User user;
        public saveUser(User user) {
            this.user=user;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            saveUserToDb(user);
            return null;
        }
    }
    public class deleteUser extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            deleteUserToLogout();
            return null;
        }
    }


    public void CreateNewItemRecyclerView(){
        books=new ArrayList<>() ;

        newBooksRecyclerView=findViewById(R.id.new_books_recyclerview);
        newItemsAdapter=new NewBooksRecyclerViewAdapter(this);
        newItemsAdapter.setItems(books);
        newBooksRecyclerView.setAdapter(newItemsAdapter);
        newBooksRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        newBooksRecyclerView.setHasFixedSize(true);
    }

    public class CreateNewBooksItems extends AsyncTask<Void,Void,Void>{
        User user;
        public CreateNewBooksItems(User user) {
            this.user = user;
        }

        @Override
        protected Void doInBackground(Void... voids) {

             String url="http://192.168.8.101:1010/book?count=10&&api_token="+user.getApi_token();

             JsonObjectRequest request=new JsonObjectRequest(
                     Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                 @Override
                 public void onResponse(JSONObject response) {
                     try {
                         List<Book> booklist=new ArrayList<Book>();
                         JSONArray books= response.getJSONArray("data");

                         for (int i=0;i<books.length();i++){
                             JSONObject book=books.getJSONObject(i);
                             Book b=new Book();
                             b.setTitle(book.getString("title"));
                             b.setUrl(book.getString("cover_url"));
                             b.setId(book.getInt("id"));
                             b.setAuthor(book.getString("author_id"));
                             b.setTag(book.getString("tag"));
                             b.setPublished(book.getInt("publication_year"));
                             b.setRatings(book.getInt("rating_5"));
                             booklist.add(b);
                         }
                         newItemsAdapter.setItems(booklist);

                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                     Log.i("books",response.toString());
                 }
             }, new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error) {

                 }
             }
             );
             requestQueue.add(request);
            return null;
        }
    }

    public void CreateAuthorItemRecyclerView(){
        List<Author> authors=new ArrayList<>() ;

        Author a =new Author();
        a.setCover_url("upload.wikimedia.org/wikipedia/commons/thumb/5/55/James_Dashner_%2814595088277%29.jpg/220px-James_Dashner_%2814595088277%29.jpg");
        a.setName("author a");
        a.setBirth_date("Book a");
        authors.add(a);


        Author c =new Author();
        c.setCover_url("upload.wikimedia.org/wikipedia/commons/thumb/5/55/James_Dashner_%2814595088277%29.jpg/220px-James_Dashner_%2814595088277%29.jpg");
        c.setName("author a");
        c.setBirth_date("Book a");
        authors.add(c);

        Author b =new Author();
        b.setCover_url("upload.wikimedia.org/wikipedia/commons/thumb/5/55/James_Dashner_%2814595088277%29.jpg/220px-James_Dashner_%2814595088277%29.jpg");
        b.setName("author a");
        b.setBirth_date("Book a");
        authors.add(b);


        RecyclerView rc=findViewById(R.id.authors_recyclerview);
        AuthorsRecyclerViewAdapter adapter=new AuthorsRecyclerViewAdapter();
        adapter.setAuthors(authors,this);
        rc.setAdapter(adapter);
        rc.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rc.setHasFixedSize(true);

    }
}
