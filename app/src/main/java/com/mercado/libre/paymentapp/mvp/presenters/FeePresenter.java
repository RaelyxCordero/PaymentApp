package com.mercado.libre.paymentapp.mvp.presenters;

import com.mercado.libre.paymentapp.R;
import com.mercado.libre.paymentapp.mvp.models.FeeModel;
import com.mercado.libre.paymentapp.utils.ApiInterface;
import com.mercado.libre.paymentapp.utils.HttpCode;
import com.mercado.libre.paymentapp.utils.events.models.EventFeeModel;
import com.mercado.libre.paymentapp.utils.events.presenters.EventFeePresenter;
import com.mercado.libre.paymentapp.utils.events.views.PickFeeFragEvent;
import com.mercado.libre.paymentapp.utils.pojoModels.PayerCost;
import com.mercado.libre.paymentapp.utils.pojoModels.ResponseFeesPojo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by raelyx on 15/06/18.
 */

public class FeePresenter extends BasePresenter {
    /*Sigleton pattern
     * */
    private static final FeePresenter mInstance = new FeePresenter();
    private ApiInterface service;

    //Fee model will live as long as live Fee presenter
    private FeeModel model = FeeModel.getInstance();

    public static FeePresenter getInstance() {
        return mInstance;
    }

    private FeePresenter() {
        super();
        service = getRetrofit().create(ApiInterface.class);
        EventBus.getDefault().register(this);
    }

    //listen events type EventFeePresenter, triggered by views or models
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(EventFeePresenter event){

        //pre build an PickBankFragEvent
        PickFeeFragEvent response = new PickFeeFragEvent(PickFeeFragEvent.SHOW_ERROR_MESSAGE);
        response.setResponseCode(event.getResponseCode());
        response.setResponseMessage(event.getResponseMessage());

        switch (event.getEventType()){

            //if UI trigger an event requiring list of fees, get into this case
            //receiving paymentMethodId, issuerId and amount
            case EventFeePresenter.UI_GET_FEE:

                //triggering an EventFeeModel
                EventBus.getDefault().post(
                        new EventFeeModel(PUBLIC_KEY, service,
                        event.getPaymentMethodId(), event.getIssuerId(), event.getAmount())
                );
                break;

            //if BankModel return successfully a list of responseFees
            case EventFeePresenter.MODEL_SUCCES_FEES_RESPONSE:

                //get all PayerCosts from all ResponseFees
                ArrayList<PayerCost> payerFees = new ArrayList<>();
                for (ResponseFeesPojo feePojo: event.getFeesList()) {
                    payerFees.addAll(feePojo.getPayerCosts());
                }

                //add the list
                response.setPayerFees(payerFees);

                //if not empty
                if (!event.getFeesList().isEmpty())
                    //view will show it
                    response.setEventType(PickFeeFragEvent.SHOW_FEES);
                else{
                    //else, set a custom message
                    response.setEventType(PickFeeFragEvent.SHOW_EMPTY_LIST_MESSAGE);
                    response.setCustomMessage(R.string.dialog_empty_payer_list);
                }

                //and post
                EventBus.getDefault().post(
                        response
                );
                break;

            //if FeeModel had a failure
            case EventFeePresenter.MODEL_FAILURE_FEES_RESPONSE:

                //then will trigger a PickPaymentFragEvent with error message
                response.setEventType(PickFeeFragEvent.SHOW_ERROR_MESSAGE);

                //setting a custom message
                if (event.getResponseCode() == HttpCode.CODE_NO_RESPONSE_ERROR)
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
