package com.codepath.apps.restclienttemplate.models;

import androidx.room.Embedded;

public class TweetWithUser {

    /*
    To solve the problem of not getting collective data from the foreign code.Thus we
    do an embedding of the data
    */

    @Embedded
    User user;

    @Embedded(prefix = "tweet_")
    Tweet tweet;


}
