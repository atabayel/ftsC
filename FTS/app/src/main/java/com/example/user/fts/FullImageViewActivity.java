package com.example.user.fts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import iammert.com.library.Status;
import iammert.com.library.StatusView;

public class FullImageViewActivity extends AppCompatActivity {

    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
    int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;
    WebView webView;
    StatusView statusView;
    String html;
    LinearLayout linearLayout;
    LinearLayout.LayoutParams lPrm;
    String file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_image_view);
        statusView = findViewById(R.id.status);

        file = "file://" + getIntent().getStringExtra("uri");

        html = "<html><head></head><body><img src=\"file://"+ file + "\"></body></html>";
        linearLayout = findViewById(R.id.linearLayout);
        lPrm = new LinearLayout.LayoutParams(wrapContent, wrapContent);
        //linearLayout.setVisibility(View.INVISIBLE);
        statusView.setStatus(Status.LOADING);
    }


    @Override
    protected void onResume() {
        super.onResume();

//        WebView webView = new WebView(this);
//        webView.getSettings().setSupportZoom(true);
//        //webView.getSettings().setBuiltInZoomControls(true);
//        webView.setPadding(0, 0, 0, 0);
//        webView.setScrollbarFadingEnabled(true);
//        webView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        linearLayout.addView(webView, lPrm);

        ImageView imageView = findViewById(R.id.imageView);

        statusView.setStatus(Status.COMPLETE);
        Picasso.with(this)
                .load(file)
                .error(R.drawable.ic_error_outline_white_24dp)
                .placeholder(R.drawable.ic_launcher_foreground)
                //.resize(500, 500)
                .into(imageView);


        //webView.loadDataWithBaseURL(null, html, "text/html", "en_US", null);
        //statusView.setStatus(Status.COMPLETE);
        //linearLayout.setVisibility(View.VISIBLE);
    }
}
