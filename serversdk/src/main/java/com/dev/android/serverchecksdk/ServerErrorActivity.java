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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dev.android.serverchecksdk.databinding.ActivityServerErrorBinding;
import android.util.Base64;

public class ServerErrorActivity extends AppCompatActivity {

    private ActivityServerErrorBinding binding;
    private WebView webView;
    CookieManager cookieManager = CookieManager.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServerErrorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        webView = binding.webview;

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
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                cookieManager.removeAllCookies(null);
                super.onPageFinished(view, url);

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                // This method is called when there's an error loading a web page
            }
        });
    }

    public static String decodeBase64(String encodedString) {
        byte[] decodedBytes = Base64.decode(encodedString, Base64.DEFAULT);
        return new String(decodedBytes);
    }
}

