package com.mercado.libre.paymentapp.utils.events.views;

import com.mercado.libre.paymentapp.utils.pojoModels.PaymentMethodPojo;

import java.util.ArrayList;

public class PickPaymentFragEvent {
    public static final int SHOW_PAYMENTS = 0;
    public static final int SHOW_ERROR_MESSAGE = 1;

    private ArrayList<PaymentMethodPojo> paymentMethods;
    private int eventType;
    private int responseCode;
    private String responseMessage;
    private int customMessage;

    public PickPaymentFragEvent(int eventType, ArrayList<PaymentMethodPojo> paymentMethods) {
        this.eventType = eventType;
        this.paymentMethods = paymentMethods;
    }

    public PickPaymentFragEvent(int eventType) {
        this.eventType = eventType;
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

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public int getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(int customMessage) {
        this.customMessage = customMessage;
    }
}
