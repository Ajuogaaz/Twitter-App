package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import okhttp3.Headers;


public class ComposeActivity extends AppCompatActivity {


    public static  final int MAX_TWEET_LENGTH = 140;
    EditText etCompose;
    Button btnTweet;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApp.getRestClient(this);

        etCompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);

        // Set a click listener to post

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tweetContent = etCompose.getText().toString();

                if(tweetContent.isEmpty()){
                    Toast.makeText(ComposeActivity.this,
                            R.string.noEmptyTweet,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tweetContent.length() > MAX_TWEET_LENGTH){
                    Toast.makeText(ComposeActivity.this,
                            R.string.tweetTooLong,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ComposeActivity.this, tweetContent, Toast.LENGTH_SHORT).show();

                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

                    }
                });



            }
        });

        //mAKE api call to twitter
    }
}