package com.rhosseini.adakreqres.model.webService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String WEB_SERVICE_BASE_URL = "https://reqres.in/";
    private static Retrofit retrofit;

    private static Retrofit getRetrofitClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(WEB_SERVICE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static <S> S createService(Class<S> serviceClass) {
        return getRetrofitClient().create(serviceClass);
    }

}
