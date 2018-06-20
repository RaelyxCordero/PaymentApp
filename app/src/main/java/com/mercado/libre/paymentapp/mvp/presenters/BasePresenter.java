package com.mercado.libre.paymentapp.mvp.presenters;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by raelyx on 15/06/18.
 */

public class BasePresenter {

    /*All presenters extends from BasePresenter.
    All of them passes a service object to his model
    to make de service call, that object is builded in this BasePresenter constructor
    * */

    private static final long TIMEOUT = 60;
    private static final String URL_BASE = "https://api.mercadopago.com/v1/";
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
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }
}
