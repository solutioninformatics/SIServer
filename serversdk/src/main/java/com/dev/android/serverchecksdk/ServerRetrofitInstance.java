package com.dev.android.serverchecksdk;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerRetrofitInstance    {
    public static String userAgent = "";
    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static OkHttpClient getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new HeaderInterceptor(userAgent))
                .connectTimeout(1, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(1, TimeUnit.MINUTES) // write timeout
                .readTimeout(1, TimeUnit.MINUTES) // read timeout
                .build();
    }

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://solutioninformatics.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getClient())
            .build();

    public static ServerAPI api = retrofit.create(ServerAPI.class);
}
