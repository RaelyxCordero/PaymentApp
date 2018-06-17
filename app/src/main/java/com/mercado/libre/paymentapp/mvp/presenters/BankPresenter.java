package com.mercado.libre.paymentapp.mvp.presenters;

import com.mercado.libre.paymentapp.utils.ApiInterface;
import com.mercado.libre.paymentapp.utils.events.bank.EventBankModel;
import com.mercado.libre.paymentapp.utils.events.bank.EventBankPresenter;
import com.mercado.libre.paymentapp.mvp.models.BankModel;
import com.mercado.libre.paymentapp.utils.events.views.PickBankFragEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by raelyx on 15/06/18.
 */

public class BankPresenter extends BasePresenter {
    private static final BankPresenter mInstance = new BankPresenter();
    private ApiInterface service;
    private BankModel model = BankModel.getInstance();

    public static BankPresenter getInstance() {
        return mInstance;
    }

    private BankPresenter() {
        super();
        service = getRetrofit().create(ApiInterface.class);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(EventBankPresenter eventBank){

        switch (eventBank.getEventType()){
            case EventBankPresenter.UI_GET_BANKS:
                EventBus.getDefault().post(
                        new EventBankModel(PUBLIC_KEY,
                                eventBank.getPaymentMethodId(), service)
                );
                break;

            case EventBankPresenter.MODEL_SUCCES_BANK_RESPONSE:
                EventBus.getDefault().post(
                        new PickBankFragEvent(PickBankFragEvent.SHOW_BANKS, eventBank.getBanksList())
                );
                break;

            case EventBankPresenter.MODEL_FAILURE_BANK_RESPONSE:
                EventBus.getDefault().post(
                        new PickBankFragEvent(PickBankFragEvent.SHOW_ERROR_MESSAGE)
                );
                break;

        }
    }
}
