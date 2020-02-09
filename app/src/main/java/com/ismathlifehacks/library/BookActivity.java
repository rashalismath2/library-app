package com.ismathlifehacks.library;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.ismathlifehacks.library.Fragments.BookDetailsFragment;
import com.ismathlifehacks.library.Fragments.BookReviewFragment;
import com.ismathlifehacks.library.Model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Book book;
    private ImageView cover;
    private TextView title,tag,author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Book");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.book_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.book_tabs);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent=getIntent();
        this.book= (Book) intent.getSerializableExtra("Book");


        cover=findViewById(R.id.book_cover);
        title=findViewById(R.id.book_title);
        tag=findViewById(R.id.book_tag);
        author=findViewById(R.id.book_author);

        LoadData(book);
    }


    void LoadData(Book book){
        Picasso.get()
                .load(book.getUrl())
                .into(cover);
        title.setText(book.getTitle());
        tag.setText("- "+book.getTag());
        author.setText("- "+book.getAuthor());

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    BookDetailsFragment fragment=new BookDetailsFragment();
                    fragment.setBook(book);
                    return fragment;
                case 1:
                    BookReviewFragment fragmentReview=new BookReviewFragment();
                    fragmentReview.setBook(book);
                    return fragmentReview;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Details";
                case 1:
                    return "Reviews";
                default:
                    return null;
            }
        }
    }
}
