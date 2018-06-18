package com.mercado.libre.paymentapp.mvp.presenters;

import com.mercado.libre.paymentapp.R;
import com.mercado.libre.paymentapp.utils.ApiInterface;
import com.mercado.libre.paymentapp.utils.HttpCode;
import com.mercado.libre.paymentapp.utils.events.models.EventBankModel;
import com.mercado.libre.paymentapp.utils.events.presenters.EventBankPresenter;
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
        PickBankFragEvent response = new PickBankFragEvent(PickBankFragEvent.SHOW_ERROR_MESSAGE);
        response.setResponseCode(eventBank.getResponseCode());
        response.setResponseMessage(eventBank.getResponseMessage());

        switch (eventBank.getEventType()){
            case EventBankPresenter.UI_GET_BANKS:
                EventBus.getDefault().post(
                        new EventBankModel(PUBLIC_KEY,
                                eventBank.getPaymentMethodId(), service)
                );
                break;

            case EventBankPresenter.MODEL_SUCCES_BANK_RESPONSE:

                response.setBanks(eventBank.getBanksList());
                if (!eventBank.getBanksList().isEmpty())
                    response.setEventType(PickBankFragEvent.SHOW_BANKS);
                else{
                    response.setEventType(PickBankFragEvent.SHOW_EMPTY_LIST_MESSAGE);
                    response.setCustomMessage(R.string.dialog_empty_bank_list);
                }

                EventBus.getDefault().post(
                        response
                );
                break;

            case EventBankPresenter.MODEL_FAILURE_BANK_RESPONSE:

                response.setEventType(PickBankFragEvent.SHOW_ERROR_MESSAGE);

                if (eventBank.getResponseCode() == HttpCode.CODE_NO_RESPONSE_ERROR)
                    response.setCustomMessage(R.string.dialog_error_no_connection);
                else
                    response.setCustomMessage(R.string.dialog_error_500);

                EventBus.getDefault().post(
                        response
                );
                break;

        }
    }
}
