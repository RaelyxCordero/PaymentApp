package com.mercado.libre.paymentapp.mvp.views.viewModels;

import android.arch.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
    private boolean tbStatus = false;

    public boolean isTbStatus() {
        return tbStatus;
    }

    public void setTbStatus(boolean tbStatus) {
        this.tbStatus = tbStatus;
    }
}
