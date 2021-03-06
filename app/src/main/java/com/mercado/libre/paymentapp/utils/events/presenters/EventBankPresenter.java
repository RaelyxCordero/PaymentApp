package com.mercado.libre.paymentapp.utils.events.presenters;

import com.mercado.libre.paymentapp.utils.pojoModels.BancoPojo;

import java.util.ArrayList;

/**
 * Created by raelyx on 15/06/18.
 */

public class EventBankPresenter {
    public static final int UI_GET_BANKS = 0;
    public static final int MODEL_SUCCES_BANK_RESPONSE = 1;
    public static final int MODEL_FAILURE_BANK_RESPONSE = 2;

    private int eventType;

    private String paymentMethodId;
    private ArrayList<BancoPojo> banksList;
    private int responseCode;
    private String responseMessage;

    public EventBankPresenter(int eventType) {
        this.eventType = eventType;
    }

    public EventBankPresenter(int eventType, String paymentMethodId) {
        this.eventType = eventType;
        this.paymentMethodId = paymentMethodId;
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

    public ArrayList<BancoPojo> getBanksList() {
        return banksList;
    }

    public void setBanksList(ArrayList<BancoPojo> banksList) {
        this.banksList = banksList;
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
}
