package com.appler.universitysystem.base;


import com.appler.universitysystem.BuildConfig;
import com.appler.universitysystem.Global;
import com.appler.universitysystem.api.ApiService;
import com.appler.universitysystem.base.converter.ResponseConvertFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


public class BaseRetrofit {

    public static final long TIMEOUT_CONNECT = 30 * 1000;
    private static ApiService apiService;
    private String baseUrl = Global.URL;

    public BaseRetrofit() {
    }

    public Retrofit get() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ResponseConvertFactory.create());
        return builder.build();
    }

    public ApiService getApiService() {
        if (apiService == null) {
            apiService = get().create(ApiService.class);
        }
        return apiService;
    }

    public OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE));
        return builder.build();
    }
}
