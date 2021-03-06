package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {

    // list the attributes
    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;

    public String bannerurl;
    public String followerCount;
    public String followingCount;
    public String likeCount;
    public String tweetCount;


    //deseriazlize the JSON
    public static User fromJson (JSONObject json) throws JSONException{
        User user= new User();

        //extract and fill the values
        user.name= json.getString("name");
        user.uid= json.getLong("id");
        user.screenName= json.getString("screen_name");
        user.profileImageUrl= json.getString("profile_image_url");



        user.followerCount=json.getString("followers_count");
        user.followingCount= json.getString("friends_count");
        user.likeCount=json.getString("favourites_count");
        user.tweetCount=json.getString("statuses_count");


        return user;

    }




}
