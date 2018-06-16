package com.mercado.libre.paymentapp.utils.events.views;

import com.mercado.libre.paymentapp.utils.pojoModels.PaymentMethodPojo;

import java.util.ArrayList;

public class PickPaymentFragEvent {
    public static final int SHOW_PAYMENTS = 0;
    public static final int SHOW_ERROR_MESSAGE = 1;

    private ArrayList<PaymentMethodPojo> paymentMethods;

    private int eventType;

    public PickPaymentFragEvent(int eventType, ArrayList<PaymentMethodPojo> paymentMethods) {
        this.eventType = eventType;
        this.paymentMethods = paymentMethods;
    }

    public int getEventType() {
        return eventType;
    }

    public ArrayList<PaymentMethodPojo> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(ArrayList<PaymentMethodPojo> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
