package com.ismathlifehacks.library;

import android.content.Context;
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
import com.ismathlifehacks.library.Model.Author;
import com.ismathlifehacks.library.Model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AuthorsRecyclerViewAdapter extends RecyclerView.Adapter<AuthorsRecyclerViewAdapter.ViewHolder> {

    private List<Author> authors;
    private Context context;

    public void setAuthors(List<Author> authors, Context context){
        this.authors=authors;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.authors_items,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Author author=authors.get(i);
        Picasso.get()
                .load("https://"+author.getCover_url())
                .into(viewHolder.cover);

        viewHolder.name.setText(author.getName());
        viewHolder.birth_date.setText(author.getBirth_date());

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),author.getName(),Toast.LENGTH_SHORT).show();;
            }
        });
    }

    @Override
    public int getItemCount() {
        return authors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView cover;
        public TextView name,birth_date;
        public LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cover=itemView.findViewById(R.id.author_cover);
            birth_date=itemView.findViewById(R.id.author_birthdate);
            name=itemView.findViewById(R.id.author_name);
            parentLayout=itemView.findViewById(R.id.authors_recycler);
        }
    }
}
