package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.github.scribejava.apis.TwitterApi;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {

    public static final String TAG = "TimelineActivity";
    TwitterClient client;
    RecyclerView rvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApp.getRestClient(this);


        //Find the Recycle View
        rvTweets = findViewById(R.id.rvTweets);
        //Innitialize the list of tweets and adapter
        //Recycler view setup: layout manager and the adapter



        populateHomeTimeline();
    }

    private void populateHomeTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {

                Log.i(TAG, "onSuccess!" + json.toString());

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

                Log.e(TAG, "onFailure" + response, throwable);

            }
        });
    }
}