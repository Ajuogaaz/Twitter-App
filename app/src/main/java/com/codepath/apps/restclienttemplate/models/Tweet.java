package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity=User.class, parentColumns = "id", childColumns = "userId"))
public class Tweet {

    @ColumnInfo
    @PrimaryKey
    public long id;

    @ColumnInfo
    public String body;

    @ColumnInfo
    public int retweetCount;

    @ColumnInfo
    public int likeCount;

    @ColumnInfo
    public String createdAt;

    @ColumnInfo
    public long userId;

    @ColumnInfo
    public String media_url;

    @ColumnInfo
    public boolean hasMedia_url;

    @Ignore
    public User user;

    public Tweet() {

    }
    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        tweet.body = jsonObject.getString("text");
        tweet.createdAt = getRelativeTimeAgo(jsonObject.getString("created_at"));
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.id = jsonObject.getLong("id");
        tweet.userId = tweet.user.id;
        tweet.retweetCount = jsonObject.getInt("retweet_count");
        tweet.likeCount = jsonObject.getInt("favorite_count");
        tweet.media_url = getInsideData(jsonObject.getJSONObject("entities"));
        if(tweet.media_url == ""){
            tweet.hasMedia_url = false;
        }else{
            tweet.hasMedia_url = true;
        }
        return tweet;
    }

    //This a method to extract the url form the api
    private static String getInsideData(JSONObject entities) throws JSONException {

        if (entities.has("media")){
            JSONObject media  = entities.getJSONArray("media").getJSONObject(0);

            return media.getString("media_url_https");

        }else{

            return "";
        }

    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {

        List<Tweet> tweets = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++ ){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;

    }

    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        relativeDate = relativeDate.replace(" minutes ago", "m");
        relativeDate = relativeDate.replace(" hours ago", "h");
        relativeDate = relativeDate.replace(" minute ago", "m");
        relativeDate = relativeDate.replace(" hour ago", "h");
        relativeDate = relativeDate.replace(" seconds ago", "s");

        return relativeDate;
    }

}
