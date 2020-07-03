package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.databinding.ActivityDetailsBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailsActivity extends AppCompatActivity {

    Tweet tweet;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityDetailsBinding binding = ActivityDetailsBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();

        client = TwitterApp.getRestClient(this);

        setContentView(view);

        tweet = Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));

        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .into(binding.ivProfileImage);

        binding.tvName.setText(tweet.user.name);
        binding.tvScreenName.setText(tweet.user.screenName);
        binding.createdAt.setText(tweet.createdAt);
        binding.tvBody.setText(tweet.body);
        binding.numberOfActualLikes.setText(tweet.likeCount);
        binding.numberOfActualRetweets.setText(tweet.retweetCount);


        binding.likeTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.likeThisTweet(tweet.id, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i("DetailsActivity", "Onsuccess to like tweet");
                        binding.likeTweet.setImageResource(R.drawable.ic_vector_heart);

                    }
                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e("DetailsActivity", response, throwable);
                    }
                });
            }
        });


        binding.reTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.retweetTweet(tweet.id, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i("DetailsActivity", "Onsuccess to like tweet");
                        binding.reTweet.setImageResource(R.drawable.ic_vector_retweet);

                    }
                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e("DetailsActivity", response, throwable);
                    }
                });
            }
        });




        Glide.with(this)
                .load(tweet.media_url)
                .into(binding.tweetPicture);

        if (tweet.hasMedia_url){
            binding.tweetPicture.setVisibility(View.VISIBLE);
            binding.roundedContainer.setVisibility(View.VISIBLE);


        } else {
            binding.tweetPicture.setVisibility(View.GONE);
            binding.roundedContainer.setVisibility(View.GONE);
        }

    }


}