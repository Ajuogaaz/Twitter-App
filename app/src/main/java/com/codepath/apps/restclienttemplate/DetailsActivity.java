package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.databinding.ActivityDetailsBinding;
import com.codepath.apps.restclienttemplate.databinding.ActivityTimelineBinding;
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

        Glide.with(this)
                .load(tweet.media_url)
                .into(binding.tweetPicture);


    }
}