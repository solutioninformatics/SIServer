package com.dev.android.serverchecksdk;

import androidx.annotation.Keep;
import retrofit2.Call;
import retrofit2.http.GET;
@Keep
public interface ServerAPI {

    @GET("serverSdk")
    Call<ServerStatus> serverStatus();
}