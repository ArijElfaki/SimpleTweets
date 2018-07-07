package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    Context context;


    AsyncHttpResponseHandler handler= new JsonHttpResponseHandler();
    TwitterClient client = TwitterApp.getRestClient(context);

    private final handleTweets handleTweets;


    interface handleTweets{
        void onItemClicked(Tweet tweet, Context context);
    }

    //pass in the Tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets, handleTweets handleTweets){
        mTweets= tweets;
        this.handleTweets= handleTweets;
    }

    //for each row, inflate the layout and cache references into ViewHolder

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);

        View tweetView= inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder= new ViewHolder(tweetView);
        return viewHolder;
    }

    // bind the values based on the position of the element

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        // get the data according to position
        Tweet tweet= mTweets.get(position);

        //populate the views according to this data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);

        holder.timeStamp.setText(getRelativeTimeAgo(tweet.createdAt));
        holder.handle.setText(" @"+tweet.handle);
        holder.likes.setText(tweet.likes);
        holder.retweets.setText(tweet.retweets);


        holder.retweetIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.retweetTweet(mTweets.get(position).uid, handler);

            }
        });

        holder.likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.likeTweet(mTweets.get(position).uid, handler);
            }
        });

        GlideApp.with(context).load(tweet.user.profileImageUrl).transform(new RoundedCorners(70))
                .into(holder.ivProfileImage);


        holder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(context, ProfileActivity.class);
                i.putExtra(User.class.getSimpleName(), Parcels.wrap(mTweets.get(position).user));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }


    // create ViewHolder class

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView timeStamp;
        public TextView handle;
        public TextView likes;
        public TextView retweets;
        public ImageView retweetIcon;
        public ImageView likeIcon;

        public ViewHolder (View itemView){
            super(itemView);

            // perform findViewById lookups

            ivProfileImage= (ImageView)itemView.findViewById(R.id.ivProfileImage);
            tvUsername= (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody= (TextView) itemView.findViewById(R.id.tvBody);

            timeStamp= (TextView)itemView.findViewById(R.id.tvTimeStamp);

            handle= (TextView)itemView.findViewById(R.id.userName);
            likes= (TextView)itemView.findViewById(R.id.likes);
            retweets= (TextView)itemView.findViewById(R.id.retweet);
            retweetIcon= (ImageView)itemView.findViewById(R.id.ivRetweets);
            likeIcon= (ImageView)itemView.findViewById(R.id.ivLikes);
            tvBody.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {


            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Tweet tweet = mTweets.get(position);
                // create intent for the new activity
               /* Intent intent = new Intent(context, DetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra("tweet", Parcels.wrap(tweet));
                context.startActivity(intent); */
               handleTweets.onItemClicked(tweet, context);

            }

        }

    }


    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return relativeDate;
    }

    /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }


}
