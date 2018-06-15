package com.mercado.libre.paymentapp.utils;

import com.mercado.libre.paymentapp.utils.pojoModels.BancoPojo;
import com.mercado.libre.paymentapp.utils.pojoModels.PaymentMethodPojo;
import com.mercado.libre.paymentapp.utils.pojoModels.ResponseFeesPojo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by raelyx on 15/06/18.
 */

public interface ApiInterface {

    @GET("/payment_methods?{public_key}")
    Call<ArrayList<PaymentMethodPojo>>
    getPaymentMethod(@Path("public_key") String publicKey);

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
