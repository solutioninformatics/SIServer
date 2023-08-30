package com.dev.android.serverchecksdk;

import androidx.annotation.Keep;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
@Keep
public interface ServerAPI {
    @GET("{path}")
    Call<ServerStatus> serverStatus(@Path("path") String path);
}
