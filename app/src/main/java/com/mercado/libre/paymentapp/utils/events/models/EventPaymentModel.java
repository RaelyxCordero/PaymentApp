package com.mercado.libre.paymentapp.utils.events.models;

import com.mercado.libre.paymentapp.utils.ApiInterface;

/**
 * Created by raelyx on 15/06/18.
 */

public class EventPaymentModel {
    private String publicKey;
    private ApiInterface service;

    public EventPaymentModel(String publicKey, ApiInterface service) {
        this.publicKey = publicKey;
        this.service = service;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public ApiInterface getService() {
        return service;
    }

    public void setService(ApiInterface service) {
        this.service = service;
    }
}
