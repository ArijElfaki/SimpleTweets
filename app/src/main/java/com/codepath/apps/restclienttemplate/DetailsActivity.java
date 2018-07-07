package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class DetailsActivity extends AppCompatActivity {

    TextView body;
    ImageView profileImage;
    TextView userName;
    TextView timeStamp;
    TextView handle;
    TextView retweetCount;
    TextView likesCount;
    EditText replyTweet;
    TextView charCount;
    Tweet tweet;

    TwitterClient client;
    AsyncHttpResponseHandler handler;


    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            charCount.setText(String.valueOf(s.length())+" /280 characters");
        }

        public void afterTextChanged(Editable s) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        client= new TwitterClient(this);
        handler= new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    tweet= Tweet.fromJson(response);
                    Intent i= new Intent(DetailsActivity.this, TimelineActivity.class);
                    i.putExtra(Parcels.class.getSimpleName(), Parcels.wrap(tweet));
                    setResult(20, i);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                finish();
            }
        };

        tweet= Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        body = (TextView)findViewById(R.id.body);
        profileImage = (ImageView)findViewById(R.id.profileImage);
        userName =  (TextView)findViewById(R.id.userName);
        timeStamp= (TextView)findViewById(R.id.timeStamp);
        handle =(TextView)findViewById(R.id.handle);
        retweetCount= (TextView)findViewById(R.id.retweetCount);
        likesCount= (TextView)findViewById(R.id.likesCount);
        replyTweet= (EditText)findViewById(R.id.replyText);
        replyTweet.addTextChangedListener(mTextEditorWatcher);
        charCount= (TextView)findViewById(R.id.count);

        body.setText(tweet.body);
        userName.setText("@"+tweet.user.screenName);
        timeStamp.setText(tweet.createdAt);
        handle.setText(tweet.user.name);
        retweetCount.setText(tweet.retweets);
        likesCount.setText(tweet.likes);
        replyTweet.setText("@"+tweet.user.screenName);
        GlideApp.with(this).load(tweet.user.profileImageUrl).transform(new RoundedCorners(90))
                .into(profileImage);

    }

    public void retweet(View view){
        client.retweetTweet(tweet.uid, handler);
    }

    public void like(View view){
        client.likeTweet(tweet.uid,handler);
    }


    public void reply(View view) {
        String message= replyTweet.getText().toString();
        client.sendTweet(message, handler);
    }

    public void exitReply(View view) {
        Intent i= new Intent(DetailsActivity.this, TimelineActivity.class);
        startActivity(i);
    }




}
