package com.mercado.libre.paymentapp.utils.events.views;

import com.mercado.libre.paymentapp.utils.pojoModels.BancoPojo;
import com.mercado.libre.paymentapp.utils.pojoModels.PaymentMethodPojo;

import java.util.ArrayList;

public class PickBankFragEvent {
    public static final int SHOW_BANKS= 0;
    public static final int SHOW_ERROR_MESSAGE = 1;
    public static final int SHOW_EMPTY_LIST_MESSAGE = 2;

    private ArrayList<BancoPojo> banks;
    private int eventType;
    private int responseCode;
    private String responseMessage;
    private int customMessage;

    public PickBankFragEvent(int eventType, ArrayList<BancoPojo> banks) {
        this.eventType = eventType;
        this.banks = banks;
    }

    public PickBankFragEvent(int eventType) {
        this.eventType = eventType;
    }



    public int getEventType() {
        return eventType;
    }

    public ArrayList<BancoPojo> getBanks() {
        return banks;
    }

    public void setBanks(ArrayList<BancoPojo> banks) {
        this.banks = banks;
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
