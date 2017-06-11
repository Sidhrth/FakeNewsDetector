package com.example.krishnakaliappan.test4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class webview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        Intent intent = getIntent();
        String link = intent.getStringExtra("EXTRA_MESSAGE");


        final WebView webView;
        setContentView(R.layout.activity_webview);
        webView = (WebView) findViewById(R.id.webpage);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        String url = "https://www.google.co.in/search?q=" + "site:smhoaxslayer.com | site:snopes.com | site:check4spam.com " +link;
        webView.loadUrl(url);
        webView.getSettings().setUseWideViewPort(true);
    }
}
