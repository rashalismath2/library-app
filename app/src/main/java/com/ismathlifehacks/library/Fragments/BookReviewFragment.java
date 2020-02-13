package com.ismathlifehacks.library.Fragments;

import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.ismathlifehacks.library.Model.Book;
import com.ismathlifehacks.library.R;
import com.ismathlifehacks.library.Youtubeconfig;

import java.util.ArrayList;


public class BookReviewFragment extends Fragment {

    private YouTubePlayerSupportFragment youTubePlayerFragment;
    private ArrayList<String> youtubeVideoArrayList;
    private YouTubePlayer youTubePlayer;

    private Book book;

    public BookReviewFragment() {
        // Required empty public constructor
    }

    public void setBook(Book book){
        this.book=book;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeYoutubePlayer();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_book_review, container, false);
        return view;
    }

    private void initializeYoutubePlayer() {


        youTubePlayerFragment = (YouTubePlayerSupportFragment) getFragmentManager()
                .findFragmentById(R.id.youtube_player_fragment);

        if (youTubePlayerFragment == null)
            return;

        youTubePlayerFragment.initialize(Youtubeconfig.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                                boolean wasRestored) {
                if (!wasRestored) {
                    youTubePlayer = player;
                    //set the player style default
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    youTubePlayer.loadVideo(book.getReview_id());
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                //print or show error if initialization failed
                Log.e("youtube err", "Youtube Player View initialization failed");
            }
        });
    }


}
