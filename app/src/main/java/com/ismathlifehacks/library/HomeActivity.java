package com.ismathlifehacks.library;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ismathlifehacks.library.Entity.User;
import com.ismathlifehacks.library.Model.Author;
import com.ismathlifehacks.library.Model.Book;
import com.ismathlifehacks.library.ViewModel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        drawer=findViewById(R.id.my_drawer);
        NavigationView myNav=findViewById(R.id.my_nav);
        myNav.setNavigationItemSelectedListener(this);

        Intent i=getIntent();
        User user= (User) i.getSerializableExtra("user");

        if(user!=null){
            new saveUser(user).execute();
        }

        CreateNewItemRecyclerView();
        CreateAuthorItemRecyclerView();
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
                new deleteUser().execute();
                Intent main=new Intent(this,MainActivity.class);
                startActivity(main);
                finish();
                break;
        }

        return true;
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
        List<Book> books=new ArrayList<>() ;

        Book a =new Book();
        a.setUrl("https://via.placeholder.com/150");
        a.setAuthor("author a");
        a.setTitle("Book a");
        books.add(a);

        Book b =new Book();
        b.setUrl("https://via.placeholder.com/150");
        b.setAuthor("author b");
        b.setTitle("Book b");
        books.add(b);

        Book c =new Book();
        c.setUrl("https://via.placeholder.com/150");
        c.setAuthor("author c");
        c.setTitle("Book c");
        books.add(c);

        Book d =new Book();
        d.setUrl("https://via.placeholder.com/150");
        d.setAuthor("author d");
        d.setTitle("Book d");
        books.add(d);

        RecyclerView rc=findViewById(R.id.new_books_recyclerview);
        NewBooksRecyclerViewAdapter adapter=new NewBooksRecyclerViewAdapter();
        adapter.setItems(books,this);
        rc.setAdapter(adapter);
        rc.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rc.setHasFixedSize(true);

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
