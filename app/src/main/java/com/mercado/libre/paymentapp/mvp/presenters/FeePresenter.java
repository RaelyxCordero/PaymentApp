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
    private static final FeePresenter mInstance = new FeePresenter();
    private ApiInterface service;
    private FeeModel model = FeeModel.getInstance();

    public static FeePresenter getInstance() {
        return mInstance;
    }

    private FeePresenter() {
        super();
        service = getRetrofit().create(ApiInterface.class);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(EventFeePresenter event){
        PickFeeFragEvent response = new PickFeeFragEvent(PickFeeFragEvent.SHOW_ERROR_MESSAGE);
        response.setResponseCode(event.getResponseCode());
        response.setResponseMessage(event.getResponseMessage());

        switch (event.getEventType()){
            case EventFeePresenter.UI_GET_FEE:

                EventBus.getDefault().post(
                        new EventFeeModel(PUBLIC_KEY, service,
                        event.getPaymentMethodId(), event.getIssuerId(), event.getAmount())
                );
                break;

            case EventFeePresenter.MODEL_SUCCES_FEES_RESPONSE:
                ArrayList<PayerCost> payerFees = new ArrayList<>();
                for (ResponseFeesPojo feePojo: event.getFeesList()) {
                    payerFees.addAll(feePojo.getPayerCosts());
                }

                response.setPayerFees(payerFees);
                if (!payerFees.isEmpty())
                    response.setEventType(PickFeeFragEvent.SHOW_FEES);
                else{
                    response.setEventType(PickFeeFragEvent.SHOW_EMPTY_LIST_MESSAGE);
                    response.setCustomMessage(R.string.dialog_empty_payer_list);
                }

                EventBus.getDefault().post(
                        response
                );
                break;

            case EventFeePresenter.MODEL_FAILURE_FEES_RESPONSE:
                response.setEventType(PickFeeFragEvent.SHOW_ERROR_MESSAGE);

                if (event.getResponseCode() == HttpCode.CODE_NO_RESPONSE_ERROR)
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
