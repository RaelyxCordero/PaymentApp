package com.mercado.libre.paymentapp.utils.events.fees;

import com.mercado.libre.paymentapp.utils.pojoModels.PaymentMethodPojo;
import com.mercado.libre.paymentapp.utils.pojoModels.ResponseFeesPojo;

import java.util.ArrayList;

/**
 * Created by raelyx on 15/06/18.
 */

public class EventFeePresenter {
    public static final int UI_GET_FEE = 0;
    public static final int MODEL_SUCCES_FEES_RESPONSE = 1;
    public static final int MODEL_FAILURE_FEES_RESPONSE = 2;

    private int eventType;

    private String paymentMethodId;
    private String issuerId;
    private int amount;
    private ArrayList<ResponseFeesPojo> feesList;

    public EventFeePresenter(int eventType) {
        this.eventType = eventType;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
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

    public ArrayList<ResponseFeesPojo> getFeesList() {
        return feesList;
    }

    public void setFeesList(ArrayList<ResponseFeesPojo> feesList) {
        this.feesList = feesList;
    }
}
