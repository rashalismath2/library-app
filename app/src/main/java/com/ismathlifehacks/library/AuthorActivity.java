package com.ismathlifehacks.library;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ismathlifehacks.library.Entity.User;
import com.ismathlifehacks.library.Model.Author;
import com.ismathlifehacks.library.Model.Book;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AuthorActivity extends AppCompatActivity {

    Toolbar toolbar;
    Author author;
    User user;
    RecyclerView newBooksRecyclerView;
    NewBooksRecyclerViewAdapter newItemsAdapter;
    private RequestQueue requestQueue;
    TextView name,nationality,books_count,description;
    ImageView cover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        toolbar = (Toolbar) findViewById(R.id.toolbar_author);
        toolbar.setTitle("Author");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestQueue=Volley.newRequestQueue(this);

        name=findViewById(R.id.author_name);
        nationality=findViewById(R.id.author_nationality);
        books_count=findViewById(R.id.author_book_counts);
        description=findViewById(R.id.author_description);
        cover=findViewById(R.id.book_book_cover);

        Intent intent=getIntent();
        this.author= (Author) intent.getSerializableExtra("Author");
        this.user= (User) intent.getSerializableExtra("User");

        name.setText(author.getName());
        nationality.setText(author.getNationality());
        books_count.setText("0");
        description.setText(author.getDescription());

        Picasso.get()
                .load("https://"+author.getCover_url())
                .into(cover);

        initiateBooks();
        new CreateNewBooksItems(user,author,books_count).execute();
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    private void initiateBooks() {
        List<Book> books=new ArrayList<>() ;

        newBooksRecyclerView=findViewById(R.id.authors_books_recyclerview);
        newItemsAdapter=new NewBooksRecyclerViewAdapter(this);
        newItemsAdapter.setItems(books);
        newBooksRecyclerView.setAdapter(newItemsAdapter);
        newBooksRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        newBooksRecyclerView.setHasFixedSize(true);
    }


    public class CreateNewBooksItems extends AsyncTask<Void,Void,Void> {
        User user;
        TextView tv;
        Author author;

        public CreateNewBooksItems(User user, Author author,TextView tv) {
            this.tv=tv;
            this.user = user;
            this.author = author;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String url="http://192.168.8.101:1010/author/"+author.getId()+"/books?count=10&&api_token="+user.getApi_token();

            JsonObjectRequest request=new JsonObjectRequest(
                    Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        List<Book> booklist=new ArrayList<Book>();
                        JSONArray books= response.getJSONArray("data");

                        tv.setText(books.length()+" Books");

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




}
