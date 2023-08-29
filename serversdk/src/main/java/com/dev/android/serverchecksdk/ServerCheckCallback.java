package com.dev.android.serverchecksdk;

public interface ServerCheckCallback {
    void onResult(boolean isServerOk,String message);
}
