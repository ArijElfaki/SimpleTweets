package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.User;

import org.parceler.Parcels;

public class ProfileActivity extends AppCompatActivity {

    TextView twitterName;
    TextView userName;
    TextView followers;
    TextView following;
    TextView likes;
    TextView tweets;

    ImageView banner;
    ImageView icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        User user= Parcels.unwrap(getIntent().getParcelableExtra(User.class.getSimpleName()));

        twitterName= (TextView) findViewById(R.id.twitterName);
        userName= (TextView) findViewById(R.id.profileUserName);
        followers= (TextView) findViewById(R.id.followers);
        following= (TextView) findViewById(R.id.following);
        likes= (TextView) findViewById(R.id.likes);
        tweets= (TextView) findViewById(R.id.tweets);

        banner= (ImageView)findViewById(R.id.ivBanner);
        icon= (ImageView)findViewById(R.id.ivIcon);


        twitterName.setText(user.name);
        userName.setText("@"+user.screenName);
        followers.setText(user.followerCount);
        following.setText(user.followingCount);
        likes.setText(user.likeCount);
        tweets.setText(user.tweetCount);




      GlideApp.with(this).load(user.profileImageUrl).transform(new RoundedCorners(270))
               .into(icon);

        GlideApp.with(this).load(user.bannerurl).into(banner);

    }
}
