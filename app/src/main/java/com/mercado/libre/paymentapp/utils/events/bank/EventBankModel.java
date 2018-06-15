package com.mercado.libre.paymentapp.utils.events.bank;

import com.mercado.libre.paymentapp.utils.ApiInterface;

/**
 * Created by raelyx on 15/06/18.
 */

public class EventBankModel {
    private String publicKey;
    private String paymentId;
    private ApiInterface service;

    public EventBankModel(String publicKey, String paymentId, ApiInterface service) {
        this.publicKey = publicKey;
        this.paymentId = paymentId;
        this.service = service;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public ApiInterface getService() {
        return service;
    }

    public void setService(ApiInterface service) {
        this.service = service;
    }
}
