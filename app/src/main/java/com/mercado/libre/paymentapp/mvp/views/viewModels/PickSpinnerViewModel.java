package com.mercado.libre.paymentapp.mvp.views.viewModels;

import android.arch.lifecycle.ViewModel;

import com.mercado.libre.paymentapp.utils.pojoModels.PaymentMethodPojo;

import java.util.ArrayList;

public class PickSpinnerViewModel extends ViewModel {
    private ArrayList spinnerList;
    private int selectedSpinnerItem;

    public ArrayList getSpinnerList() {
        return spinnerList;
    }

    public void setSpinnerList(ArrayList spinnerList) {
        this.spinnerList = spinnerList;
    }

    public int getSelectedSpinnerItem() {
        return selectedSpinnerItem;
    }

    public void setSelectedSpinnerItem(int selectedSpinnerItem) {
        this.selectedSpinnerItem = selectedSpinnerItem;
    }
}
