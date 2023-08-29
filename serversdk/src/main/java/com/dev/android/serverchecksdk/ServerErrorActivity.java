package com.dev.android.serverchecksdk;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieSyncManager;
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

        webView.loadUrl(decodeBase64(Util.companion.backUpSite));

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                CookieSyncManager.getInstance().startSync();
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.textView.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                CookieSyncManager.getInstance().stopSync();
                binding.progressBar.setVisibility(View.GONE);
                binding.textView.setVisibility(View.GONE);
                super.onPageFinished(view, url);

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                binding.progressBar.setVisibility(View.GONE);
                binding.textView.setVisibility(View.VISIBLE);
                // This method is called when there's an error loading a web page
            }
        });
    }

    public static String decodeBase64(String encodedString) {
        byte[] decodedBytes = Base64.decode(encodedString, Base64.DEFAULT);
        return new String(decodedBytes);
    }
}