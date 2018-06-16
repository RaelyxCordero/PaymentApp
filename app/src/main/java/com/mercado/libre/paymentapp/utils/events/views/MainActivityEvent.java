package com.mercado.libre.paymentapp.utils.events.views;

public class MainActivityEvent {
    public static final int BACK_PRESSED = 0;
    public static final int NEXT_PRESSED = 1;

    private int eventType;

    public MainActivityEvent(int eventType) {
        this.eventType = eventType;
    }

    public int getEventType() {
        return eventType;
    }
}
