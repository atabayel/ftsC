package com.example.user.fts;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.user.fts.API;

public class InitAPI extends Application {

    private static API api;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(MyContants.BASE_URL) // Адрес сервера
                .addConverterFactory(GsonConverterFactory.create(gson)) // говорим ретрофиту что для сериализации необходимо использовать GSON
                .build();

        api = retrofit.create(API.class);
    }


    public static API getAPI(){
        return api;
    }
}
