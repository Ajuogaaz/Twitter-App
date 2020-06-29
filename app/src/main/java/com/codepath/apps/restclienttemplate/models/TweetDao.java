package com.codepath.apps.restclienttemplate.models;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public  interface TweetDao {

    //this is a sample querry.
    @Query("SELECT * FROM Tweet ORDER BY createdAt DESC LIMIT 300")
    List<TweetWithUser> recentItems();


}
