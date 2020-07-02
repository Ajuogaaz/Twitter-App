package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.databinding.ActivityDetailsBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {

    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityDetailsBinding binding = ActivityDetailsBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();

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