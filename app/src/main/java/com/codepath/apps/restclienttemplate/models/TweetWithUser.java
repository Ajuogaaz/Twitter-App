package com.codepath.apps.restclienttemplate.models;

import androidx.room.Embedded;

import java.util.ArrayList;
import java.util.List;

public class TweetWithUser {

    /*
    To solve the problem of not getting collective data from the foreign code.Thus we
    do an embedding of the data
    */

    @Embedded
    User user;

    @Embedded(prefix = "tweet_")
    Tweet tweet;


    public static List<Tweet> getTweetList(List<TweetWithUser> tweetWithUsers) {

        List<Tweet>tweets = new ArrayList<>();
        //We are seeking the specific tweets abd reconstructing them with the users inside them
        for (int i = 0; i < tweetWithUsers.size(); i++){
            Tweet tweet = tweetWithUsers.get(i).tweet;

            tweet.user = tweetWithUsers.get(i).user;

            tweets.add(tweet);
        }
        return tweets;
    }
}
