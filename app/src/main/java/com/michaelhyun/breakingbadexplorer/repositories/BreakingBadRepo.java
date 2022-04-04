package com.michaelhyun.breakingbadexplorer.repositories;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BreakingBadRepo {
    public static String BASE_URL = "https://breakingbadapi.com/";

    public static Retrofit retrofit;

    public static Retrofit getRetrofitClient() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
