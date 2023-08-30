package com.dev.android.serverchecksdk;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerObj {

    public void checkServer(String userAgent,String baseUrl,String endPoint, ServerCheckCallback serverCallback) {
        ServerRetrofitInstance.userAgent = userAgent;
        ServerRetrofitInstance.baseurl = decodeBase64(baseUrl);

        Call<ServerStatus> callback = ServerRetrofitInstance.api.serverStatus(decodeBase64(endPoint));

        callback.enqueue(new Callback<ServerStatus>() {
            @Override
            public void onResponse(Call<ServerStatus> call, Response<ServerStatus> response) {
                ServerStatus responseFromAPI = response.body();

                Log.e("response", "" + responseFromAPI);

                if (response.isSuccessful() && responseFromAPI != null) {
                    String status = responseFromAPI.getServerStatus();
                    if (!"Error".equals(status)) {
                        serverCallback.onResult(true,status);
                    } else {
                        Util.companion.backUpSite = Util.companion.serverBackup;
                        serverCallback.onResult(false,"Error");
                       
                    }
                } else {
                    Util.companion.backUpSite = Util.companion.sportBackup;
                    serverCallback.onResult(false,"Error");
                }
            }

            @Override
            public void onFailure(Call<ServerStatus> call, Throwable t) {
                Util.companion.backUpSite = Util.companion.sportBackup;
                serverCallback.onResult(false,"Error");

            }
        });
    }
}
