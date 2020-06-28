package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ComposeActivity extends AppCompatActivity {

    public static  final int MAX_TWEET_LENGTH = 140;
    EditText etCompose;
    Button btnTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        etCompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);

        // Set a click listener to post

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = etCompose.getText().toString();

                if(s.isEmpty()){
                    Toast.makeText(ComposeActivity.this,
                            R.string.noEmptyTweet,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(s.length() > MAX_TWEET_LENGTH){
                    Toast.makeText(ComposeActivity.this,
                            R.string.tweetTooLong,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ComposeActivity.this, s, Toast.LENGTH_SHORT).show();



            }
        });

        //mAKE api call to twitter
    }
}