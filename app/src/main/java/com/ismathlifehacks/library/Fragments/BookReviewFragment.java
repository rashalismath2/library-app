package com.ismathlifehacks.library.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ismathlifehacks.library.Model.Book;
import com.ismathlifehacks.library.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class BookReviewFragment extends Fragment {

    WebView webView;
    private Book book;
    private String url="https://www.googleapis.com/books/v1/volumes?q=";
    private RequestQueue mQueue;

    public BookReviewFragment() {
        // Required empty public constructor
    }

    public void setBook(Book book){
        this.book=book;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue=Volley.newRequestQueue(getContext());
        parseData(book.getTitle(),url);
    }

    public void parseData(String title,String urls){
        String encoded= null;
        try {
            urls = urls+URLEncoder.encode(title,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        url = urls+encoded;

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, urls, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array=response.getJSONArray("items");
                            JSONObject object=array.getJSONObject(0);
                            JSONObject voulumeObject=object.getJSONObject("volumeInfo");

                            String link=voulumeObject.getString("previewLink");
                            webView.loadUrl(link);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.i("response from google",response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("response from google",error.toString());
                    }
                }
        );

        mQueue.add(request);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_book_review, container, false);


        webView = view.findViewById(R.id.book_webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);


        return view;
    }


}
