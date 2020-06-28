package com.codepath.apps.restclienttemplate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.github.scribejava.apis.TwitterApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {

    public static final String TAG = "TimelineActivity";
    TwitterClient client;
    RecyclerView rvTweets;
    List<Tweet> tweets;
    TweetsAdapter adapter;
    SwipeRefreshLayout swipeContainer;
    EndlessRecycleViewScrollListener scrollListener;
    public final int REQUEST_CODE= 20;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApp.getRestClient(this);
        

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "fetching new data");
                populateHomeTimeline();
            }
        });

        //Find the Recycle View
        rvTweets = findViewById(R.id.rvTweets);

        //Innitialize the list of tweets and adapter
        tweets = new ArrayList<>();
        adapter = new TweetsAdapter(this, tweets);

        //Recycler view setup: layout manager and the adapter

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);

        rvTweets.setLayoutManager(layoutManager);
        rvTweets.setAdapter(adapter);

        scrollListener = new EndlessRecycleViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.i(TAG, "OnloadMore" + page);
                loadMoreData();
            }
        };
        
        //Add scroll listener to the recycle View
        rvTweets.addOnScrollListener(scrollListener);
        
        
        populateHomeTimeline();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.compose){
            Toast.makeText(this, "Compose!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(TimelineActivity.this, ComposeActivity.class);


            startActivityForResult(intent, REQUEST_CODE);
        }


        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            //Get data from the intent (tweet)
            Tweet recentTweet = Parcels.unwrap(data.getParcelableExtra("tweet"));
            //Update the RV with the tweet
            //Modify data set to contain tweet
            tweets.add(0, recentTweet);
            // then notify adapter
            adapter.notifyItemInserted(0);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    private void loadMoreData() {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        client.getNextPageOfTweets(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {

                Log.i(TAG, "onSuccess for load more dATA" + json.toString());

                //  --> Deserialize and construct new model objects from the API response
                JSONArray jsonArray = json.jsonArray;
                try {
                    //  --> Append the new data objects to the existing set of items inside the array of items
                    //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()
                    List<Tweet> extraTweets = Tweet.fromJsonArray(jsonArray);
                    adapter.addAll(extraTweets);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

                Log.i(TAG, "onFailure for load more dATA", throwable);

            }
        }, tweets.get(tweets.size() - 1).id );


    }

    private void populateHomeTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {

                Log.i(TAG, "onSuccess!" + json.toString());
                JSONArray jsonArray = json.jsonArray;
                try {
                    adapter.clear();
                    adapter.addAll(Tweet.fromJsonArray(jsonArray));
                    swipeContainer.setRefreshing(false);
                } catch (JSONException e) {
                    Log.e(TAG, "JsonException", e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

                Log.e(TAG, "onFailure" + response, throwable);

            }
        });
    }
}