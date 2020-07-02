package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetsAdapter extends  RecyclerView.Adapter<TweetsAdapter.ViewHolder>{

    public interface onClickListener{
        void onItemClicked(int position);
    }

    Context context;
    List<Tweet> tweets;
    onClickListener clickListener;

    // Pass in context and list of tweets

    public TweetsAdapter(Context context, List<Tweet> tweets, onClickListener clickListener) {
        this.context = context;
        this.tweets = tweets;
        this.clickListener = clickListener;
    }

    //for each row Inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View tweetView = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(tweetView);
    }
    //Bind Values based on type position and the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Get the tweet at the particular position
        Tweet tweet = tweets.get(position);

        //Bind the tweet data into the view holder
        holder.bind(tweet);

    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }
    //Clean all tweets in the adapter
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> Tweetlist) {
        tweets.addAll(Tweetlist);
        notifyDataSetChanged();
    }


    //Define the view hOLDER
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView createdAt;
        ImageView tweetMedia;
        ImageView replyTweet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            createdAt = itemView.findViewById(R.id.createdAt);
            tweetMedia = itemView.findViewById(R.id.tweetPicture);
            replyTweet = itemView.findViewById(R.id.replyTweet);

        }

        public void bind(Tweet tweet) {

            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            createdAt.setText(tweet.createdAt);

            int radius = 30; //corner radius22
            int margin = 10;

            Glide.with(context)
                    .load(tweet.media_url)
                    .into(tweetMedia);

            Glide.with(context)
                    .load(tweet.user.profileImageUrl)
                    .into(ivProfileImage);

            if (tweet.hasMedia_url){
                tweetMedia.setVisibility(View.VISIBLE);

            } else {
                tweetMedia.setVisibility(View.GONE);
            }


            replyTweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClicked(getAdapterPosition());
                }
            });

        }
    }
}
