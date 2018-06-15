package com.mercado.libre.paymentapp.utils.events.payment;

import com.mercado.libre.paymentapp.utils.pojoModels.BancoPojo;
import com.mercado.libre.paymentapp.utils.pojoModels.PaymentMethodPojo;

import java.util.ArrayList;

/**
 * Created by raelyx on 15/06/18.
 */

public class EventPaymentPresenter {
    public static final int UI_GET_PAYMENTS = 0;
    public static final int MODEL_SUCCES_PAYMENTS_RESPONSE = 1;
    public static final int MODEL_FAILURE_PAYMENTS_RESPONSE = 2;

    private int eventType;

    private String paymentMethodId;
    private ArrayList<PaymentMethodPojo> paymentsList;

    public EventPaymentPresenter(int eventType) {
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

    public ArrayList<PaymentMethodPojo> getPaymentsList() {
        return paymentsList;
    }

    public void setPaymentsList(ArrayList<PaymentMethodPojo> paymentsList) {
        this.paymentsList = paymentsList;
    }
}
