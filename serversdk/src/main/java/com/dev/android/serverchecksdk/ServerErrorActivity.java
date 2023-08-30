package com.dev.android.serverchecksdk;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import com.dev.android.serverchecksdk.R;

public class ServerErrorActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private TextView textView;
    CookieManager cookieManager = CookieManager.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_error);

        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.loadUrl(Util.companion.backUpSite);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                cookieManager.flush();
                progressBar.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                cookieManager.removeAllCookies(null);
                progressBar.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressBar.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                // This method is called when there's an error loading a web page
            }
        });
    }

    public static String decodeBase64(String encodedString) {
        byte[] decodedBytes = Base64.decode(encodedString, Base64.DEFAULT);
        return new String(decodedBytes);
    }
}
