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
    /*Sigleton pattern
     * */
    private static final BankPresenter mInstance = new BankPresenter();
    private ApiInterface service;

    //Bank model will live as long as live Bank presenter
    private BankModel model = BankModel.getInstance();

    public static BankPresenter getInstance() {
        return mInstance;
    }

    private BankPresenter() {
        super();
        service = getRetrofit().create(ApiInterface.class);
        EventBus.getDefault().register(this);
    }

    //listen events type EventBankPresenter, triggered by views or models
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(EventBankPresenter eventBank){

        //pre build an PickBankFragEvent
        PickBankFragEvent response = new PickBankFragEvent(PickBankFragEvent.SHOW_ERROR_MESSAGE);
        response.setResponseCode(eventBank.getResponseCode());
        response.setResponseMessage(eventBank.getResponseMessage());

        switch (eventBank.getEventType()){

            //if UI trigger an event requiring list of banks, get into this case
            //receiving paymentMethodId
            case EventBankPresenter.UI_GET_BANKS:

                //triggering an EventBankModel
                EventBus.getDefault().post(
                        new EventBankModel(PUBLIC_KEY,
                                eventBank.getPaymentMethodId(), service)
                );
                break;

            //if BankModel return successfully a list of banks
            case EventBankPresenter.MODEL_SUCCES_BANK_RESPONSE:

                response.setBanks(eventBank.getBanksList());

                //if not empty, the view can show banks list
                if (!eventBank.getBanksList().isEmpty())
                    response.setEventType(PickBankFragEvent.SHOW_BANKS);
                else{
                    //else, dont show the list and send a custom message
                    response.setEventType(PickBankFragEvent.SHOW_EMPTY_LIST_MESSAGE);
                    response.setCustomMessage(R.string.dialog_empty_bank_list);
                }

                //then post
                EventBus.getDefault().post(
                        response
                );
                break;

            //if BankModel had a failure
            case EventBankPresenter.MODEL_FAILURE_BANK_RESPONSE:

                //then will trigger a PickPaymentFragEvent with error message
                response.setEventType(PickBankFragEvent.SHOW_ERROR_MESSAGE);

                //setting a custom message
                if (eventBank.getResponseCode() == HttpCode.CODE_NO_RESPONSE_ERROR)
                    response.setCustomMessage(R.string.dialog_error_no_connection);
                else
                    response.setCustomMessage(R.string.dialog_error_500);

                //and post
                EventBus.getDefault().post(
                        response
                );
                break;

        }
    }
}
