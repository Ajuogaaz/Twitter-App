package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetsAdapter extends  RecyclerView.Adapter<TweetsAdapter.ViewHolder>{

    public interface onClickListener{
        void onItemClicked(int position, int replyCode);
    }

    Context context;
    List<Tweet> tweets;
    onClickListener clickListener;
    public static final int REPLY_CODE = 100;
    public static final int DETAILS_CODE = 200;

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
        CardView imageContainer;
        TextView retweetCount;
        TextView likeCount;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            createdAt = itemView.findViewById(R.id.createdAt);
            tweetMedia = itemView.findViewById(R.id.tweetPicture);
            replyTweet = itemView.findViewById(R.id.replyTweet);
            imageContainer = itemView.findViewById(R.id.roundedContainer);
            retweetCount = itemView.findViewById(R.id.numberOfActualRetweets);
            likeCount = itemView.findViewById(R.id.numberOfActualLikes);



        }

        public void bind(Tweet tweet) {

            tvBody.setText(tweet.body);
            tvBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   clickListener.onItemClicked(getAdapterPosition(), DETAILS_CODE);
                }
            });


            tvScreenName.setText(tweet.user.name);
            tvScreenName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClicked(getAdapterPosition(), DETAILS_CODE);
                }
            });


            createdAt.setText(tweet.createdAt);


            Glide.with(context)
                    .load(tweet.media_url)
                    .into(tweetMedia);

            Glide.with(context)
                    .load(tweet.user.profileImageUrl)
                    .into(ivProfileImage);

            if (tweet.hasMedia_url){
                tweetMedia.setVisibility(View.VISIBLE);
                imageContainer.setVisibility(View.VISIBLE);

                tweetMedia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickListener.onItemClicked(getAdapterPosition(), DETAILS_CODE);
                    }
                });

            } else {
                tweetMedia.setVisibility(View.GONE);
                imageContainer.setVisibility(View.GONE);
            }


            replyTweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClicked(getAdapterPosition(), REPLY_CODE);
                }
            });




        }
    }
}
