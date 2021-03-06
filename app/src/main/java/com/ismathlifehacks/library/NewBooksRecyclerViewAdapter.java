package com.ismathlifehacks.library;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ismathlifehacks.library.Model.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewBooksRecyclerViewAdapter extends RecyclerView.Adapter<NewBooksRecyclerViewAdapter.ViewHolder> {

    private List<Book> books;
    private Context context;

    public void setItems(List<Book> books){
        this.books = books;
        notifyDataSetChanged();
    }

    public NewBooksRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_items_home,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Book book=books.get(i);
        Picasso.get()
                .load(book.getUrl())
                .into(viewHolder.cover);

        viewHolder.title.setText(book.getTitle());
        viewHolder.author.setText(book.getAuthor());

        viewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bookActivity=new Intent(context,BookActivity.class);
                bookActivity.putExtra("Book",book);
                context.startActivity(bookActivity);
            }
        });

    }


    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        protected ImageView cover;
        protected TextView title,author;
        protected LinearLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cover=itemView.findViewById(R.id.new_book_cover);
            title=itemView.findViewById(R.id.new_book_title);
            author=itemView.findViewById(R.id.new_book_author);
            parent=itemView.findViewById(R.id.new_book_item_recycler);
        }
    }
}
