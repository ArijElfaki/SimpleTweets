package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;


@Parcel
public class Tweet {

    //listing out the attributes
    public String body;
    public long uid; // database ID for the tweet
    public User user;
    public String createdAt;
    public String handle;
    public String likes;
    public String retweets;

    //deserializing the JSON

    public static Tweet fromJson (JSONObject jsonObject) throws JSONException{
        Tweet tweet= new Tweet();

        //extract the values from JSON
        tweet.body= jsonObject.getString("text");
        tweet.uid= jsonObject.getLong("id");
        tweet.createdAt= jsonObject.getString("created_at");
        tweet.user= User.fromJson(jsonObject.getJSONObject("user"));
        tweet.handle=tweet.user.screenName;
        tweet.likes= jsonObject.getString("favorite_count");
        tweet.retweets=jsonObject.getString("retweet_count");

        return tweet;
    }


}
