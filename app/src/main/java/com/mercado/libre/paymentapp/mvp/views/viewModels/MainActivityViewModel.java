package com.mercado.libre.paymentapp.mvp.views.viewModels;

import android.arch.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
    private boolean tbStatus = false;
    private boolean dialogPaymentShowed = false;

    public boolean isTbStatus() {
        return tbStatus;
    }

    public void setTbStatus(boolean tbStatus) {
        this.tbStatus = tbStatus;
    }

    public boolean isDialogPaymentShowed() {
        return dialogPaymentShowed;
    }

    public void setDialogPaymentShowed(boolean dialogPaymentShowed) {
        this.dialogPaymentShowed = dialogPaymentShowed;
    }
}
