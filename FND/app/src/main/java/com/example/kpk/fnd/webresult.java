package com.example.kpk.fnd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class webresult extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webresult);

        String url = getIntent().getStringExtra("linkurl");
        webView = (WebView) findViewById(R.id.webw);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

    }
}
