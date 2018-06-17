package com.mercado.libre.paymentapp.utils.events.views;

import com.mercado.libre.paymentapp.utils.pojoModels.BancoPojo;
import com.mercado.libre.paymentapp.utils.pojoModels.PaymentMethodPojo;

import java.util.ArrayList;

public class PickBankFragEvent {
    public static final int SHOW_BANKS= 0;
    public static final int SHOW_ERROR_MESSAGE = 1;

    private ArrayList<BancoPojo> banks;

    private int eventType;

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
}
