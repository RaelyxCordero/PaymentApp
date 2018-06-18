package com.mercado.libre.paymentapp.utils.events.views;

import com.mercado.libre.paymentapp.utils.pojoModels.BancoPojo;
import com.mercado.libre.paymentapp.utils.pojoModels.PayerCost;

import java.util.ArrayList;

public class PickFeeFragEvent {
    public static final int SHOW_FEES= 0;
    public static final int SHOW_ERROR_MESSAGE = 1;
    public static final int SHOW_EMPTY_LIST_MESSAGE = 2;

    private ArrayList<PayerCost> payerFees;
    private int eventType;
    private int responseCode;
    private String responseMessage;
    private int customMessage;

    public PickFeeFragEvent(int eventType, ArrayList<PayerCost> banks) {
        this.eventType = eventType;
        this.payerFees = banks;
    }

    public PickFeeFragEvent(int eventType) {
        this.eventType = eventType;
    }

    public ArrayList<PayerCost> getPayerFees() {
        return payerFees;
    }

    public void setPayerFees(ArrayList<PayerCost> payerFees) {
        this.payerFees = payerFees;
    }

    public int getEventType() {
        return eventType;
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
