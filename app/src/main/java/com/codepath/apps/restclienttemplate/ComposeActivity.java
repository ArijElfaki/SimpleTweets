package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    public EditText text;
    public TwitterClient client;
    public AsyncHttpResponseHandler handler;
    public TextView tvCount;
    private Tweet tweet;

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            tvCount.setText(String.valueOf(s.length())+" /280 characters");
        }

        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        text= (EditText) findViewById(R.id.tweetText);
        text.addTextChangedListener(mTextEditorWatcher);
        client= TwitterApp.getRestClient(this);
        tvCount= (TextView) findViewById(R.id.count);

        handler= new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    tweet= Tweet.fromJson(response);
                    Intent i= new Intent(ComposeActivity.this, TimelineActivity.class);
                    i.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
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
    }


    public void tweet(View view) {
        String message= text.getText().toString();
        client.sendTweet(message, handler);
    }

    public void exit(View view) {
        Intent i= new Intent(ComposeActivity.this, TimelineActivity.class);
        startActivity(i);

    }
}



//sources used in this code
// https://stackoverflow.com/questions/3013791/live-character-count-for-edittext -> charactewr listener