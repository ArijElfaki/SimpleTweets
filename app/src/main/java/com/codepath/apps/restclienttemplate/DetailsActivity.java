package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {

    TextView body;
    ImageView profileImage;
    TextView userName;
    TextView timeStamp;
    TextView handle;
    TextView retweetCount;
    TextView likesCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Tweet tweet= Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        body = (TextView)findViewById(R.id.body);
        profileImage = (ImageView)findViewById(R.id.profileImage);
        userName =  (TextView)findViewById(R.id.userName);
        timeStamp= (TextView)findViewById(R.id.timeStamp);
        handle =(TextView)findViewById(R.id.handle);
        retweetCount= (TextView)findViewById(R.id.retweetCount);
        likesCount= (TextView)findViewById(R.id.likesCount);

        body.setText(tweet.body);
        userName.setText(tweet.user.screenName);
        timeStamp.setText(tweet.createdAt);
        handle.setText(tweet.user.name);
        retweetCount.setText(tweet.retweets);
        likesCount.setText(tweet.likes);
        GlideApp.with(this).load(tweet.user.profileImageUrl).transform(new RoundedCorners(90))
                .into(profileImage);


    }
}
