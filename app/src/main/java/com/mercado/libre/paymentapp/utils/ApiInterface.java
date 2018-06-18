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

    @GET("/v1/payment_methods?")
    Call<ArrayList<PaymentMethodPojo>>
    getPaymentMethod(@Query("public_key") String publicKey);

    @GET("/v1/payment_methods/card_issuers?")
    Call<ArrayList<BancoPojo>>
    getBankByPaymentMethodId(
            @Query("public_key") String publicKey,
            @Query("payment_method_id") String paymentMethodId
    );

    @GET("/v1/payment_methods/installments?")
    Call<ArrayList<ResponseFeesPojo>>
    getFees(@Query("public_key") String publicKey,
            @Query("amount") Integer amount,
            @Query("payment_method_id") String paymentMethodId,
            @Query("issuer.id") String idIssuer
    );


}
