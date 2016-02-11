package com.example.joao.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.example.joao.myapplication.R;

/**
 * Created by joao on 10/02/2016.
 */
public class RepDescActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_desc);
        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        WebView myWebView = (WebView) findViewById(R.id.webviewUser);
        myWebView.loadUrl(url);




    }
}
