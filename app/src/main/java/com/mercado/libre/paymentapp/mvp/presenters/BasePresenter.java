package com.mercado.libre.paymentapp.mvp.presenters;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by raelyx on 15/06/18.
 */

public class BasePresenter {
    private static final long TIMEOUT = 60;
    private static final String URL_BASE = "https://api.mercadopago.com/v1";
    protected static final String PUBLIC_KEY = "444a9ef5-8a6b-429f-abdf-587639155d88";
    private Retrofit retrofit;

    protected BasePresenter() {
        retrofit = buildRetrofit();
    }

    private static Retrofit buildRetrofit(){
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(okHttpClient)
                .build();
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }
}
