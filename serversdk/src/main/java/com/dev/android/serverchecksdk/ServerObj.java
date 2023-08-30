package com.dev.android.serverchecksdk;


import android.util.Base64;
import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerObj {

    public void initServer(String userAgent,String baseUrl,String endPoint, ServerCheckCallback serverCallback) {
        ServerRetrofitInstance instance = new ServerRetrofitInstance(userAgent,decodeBase64(baseUrl));

        ServerAPI apiInstance = instance.api;
        Call<ServerStatus> callback = apiInstance.serverStatus(decodeBase64(endPoint));

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
                        Util.companion.backUpSite = decodeBase64(baseUrl)+decodeBase64(endPoint)+"/server";
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

    public static String decodeBase64(String encodedString) {
        byte[] decodedBytes = Base64.decode(encodedString, Base64.DEFAULT);
        return new String(decodedBytes);
    }
}
