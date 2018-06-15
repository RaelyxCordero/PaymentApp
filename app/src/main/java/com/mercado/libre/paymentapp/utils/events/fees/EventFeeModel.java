package com.mercado.libre.paymentapp.utils.events.fees;

import com.mercado.libre.paymentapp.utils.ApiInterface;

/**
 * Created by raelyx on 15/06/18.
 */

public class EventFeeModel {
    private String publicKey;
    private String paymentId;
    private String issuerId;
    private int amount;
    private ApiInterface service;

    public EventFeeModel(String publicKey, ApiInterface service,
                         String paymentId, String issuerId, int amount) {
        this.publicKey = publicKey;
        this.service = service;
        this.paymentId = paymentId;
        this.issuerId = issuerId;
        this.amount = amount;
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

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
