package com.dev.android.serverchecksdk;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.state.State;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    public HeaderInterceptor(String userAgent) {
        Util.companion.userAgentInfo = userAgent;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest = chain.request().newBuilder()
                .addHeader("User-Agent", Util.companion.userAgentInfo)
                .build();
        return chain.proceed(newRequest);
    }
}

