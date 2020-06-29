package com.codepath.apps.restclienttemplate.models;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public  interface TweetDao {

    @Query("SELECT * FROM Tweet ORDER BY createdAt DESC LIMIT 300")
    List<TweetWithUser> recentItems();
}
