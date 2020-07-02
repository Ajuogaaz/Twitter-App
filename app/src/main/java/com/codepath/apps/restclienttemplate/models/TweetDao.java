package com.codepath.apps.restclienttemplate.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public  interface TweetDao {

    //Combine the tweets with User factoring in the fact that you prepended some data
    @Query("SELECT Tweet.body AS tweet_body, Tweet.createdAt AS tweet_createdAt, Tweet.Id AS tweet_id , Tweet.retweetCount AS tweet_retweetCount, " +
            " Tweet.likeCount AS tweet_likeCount, User.*" +
            "FROM Tweet INNER JOIN User ON  Tweet.userId = User.id ORDER BY createdAt DESC LIMIT 5")
    List<TweetWithUser> recentItems();

    // Added methods we will call to insert the dats

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(Tweet... tweets);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(User... users);


}
