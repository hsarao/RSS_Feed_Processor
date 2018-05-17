package com.example.assignment7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        TextView textView = findViewById(R.id.textView);
        textView.setText(getIntent().getStringExtra("test"));
        String url = this.getIntent().getExtras().getString("test");
        webView = (WebView) findViewById(R.id.detail_web_view);
        webView.loadUrl(url);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
