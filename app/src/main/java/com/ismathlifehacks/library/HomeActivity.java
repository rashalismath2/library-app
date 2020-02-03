package com.ismathlifehacks.library;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ismathlifehacks.library.Model.Author;
import com.ismathlifehacks.library.Model.Book;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CreateNewItemRecyclerView();
        CreateAuthorItemRecyclerView();
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
        a.setCover_url("upload.wikimedia.org/wikipedia/commons/thumb/3/3f/Book_collection.jpg/24px-Book_collection.jpg ");
        a.setName("author a");
        a.setBirth_date("Book a");
        authors.add(a);


        Author c =new Author();
        c.setCover_url("upload.wikimedia.org/wikipedia/commons/thumb/3/3f/Book_collection.jpg/24px-Book_collection.jpg ");
        c.setName("author a");
        c.setBirth_date("Book a");
        authors.add(c);

        Author b =new Author();
        b.setCover_url("upload.wikimedia.org/wikipedia/commons/thumb/3/3f/Book_collection.jpg/24px-Book_collection.jpg ");
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
