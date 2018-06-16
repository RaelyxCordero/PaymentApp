package com.mercado.libre.paymentapp.utils;

import com.mercado.libre.paymentapp.utils.pojoModels.BancoPojo;
import com.mercado.libre.paymentapp.utils.pojoModels.PaymentMethodPojo;
import com.mercado.libre.paymentapp.utils.pojoModels.ResponseFeesPojo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by raelyx on 15/06/18.
 */

public interface ApiInterface {

    @GET("/v1/payment_methods?public_key=444a9ef5-8a6b-429f-abdf-587639155d88")
    Call<ArrayList<PaymentMethodPojo>>
    getPaymentMethod(/*@Field("public_key") String publicKey*/);

    @GET("/payment_methods/card_issuers?{public_key}&{payment_method_id}")
    Call<ArrayList<BancoPojo>>
    getBankByPaymentMethodId(@Path("public_key") String publicKey,
                             @Path("payment_method_id") String paymentMethodId);

    @GET("/payment_methods/installments?{public_key}" +
            "&{amount}" +
            "&{payment_method_id}" +
            "&{issuer.id}")
    Call<ArrayList<ResponseFeesPojo>>
    getFees(@Path("public_key") String publicKey,
            @Path("amount") Integer amount,
            @Path("payment_method_id") String paymentMethodId,
            @Path("issuer.id") String idIssuer
    );


}
