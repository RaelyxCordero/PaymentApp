package com.mercado.libre.paymentapp.mvp.presenters;

import com.mercado.libre.paymentapp.R;
import com.mercado.libre.paymentapp.mvp.models.PaymentModel;
import com.mercado.libre.paymentapp.utils.ApiInterface;
import com.mercado.libre.paymentapp.utils.HttpCode;
import com.mercado.libre.paymentapp.utils.events.models.EventPaymentModel;
import com.mercado.libre.paymentapp.utils.events.presenters.EventPaymentPresenter;
import com.mercado.libre.paymentapp.utils.events.views.PickPaymentFragEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by raelyx on 15/06/18.
 */

public class PaymentPresenter extends BasePresenter {
    private static final PaymentPresenter mInstance = new PaymentPresenter();
    private ApiInterface service;
    private PaymentModel model = PaymentModel.getInstance();

    public static PaymentPresenter getInstance() {
        return mInstance;
    }

    private PaymentPresenter() {
        super();
        service = getRetrofit().create(ApiInterface.class);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(EventPaymentPresenter event){

        PickPaymentFragEvent response = new PickPaymentFragEvent(PickPaymentFragEvent.SHOW_ERROR_MESSAGE);
        response.setResponseCode(event.getResponseCode());
        response.setResponseMessage(event.getResponseMessage());

        switch (event.getEventType()){
            case EventPaymentPresenter.UI_GET_PAYMENTS:
                EventBus.getDefault().post(
                        new EventPaymentModel(PUBLIC_KEY, service)
                );
                break;

            case EventPaymentPresenter.MODEL_SUCCES_PAYMENTS_RESPONSE:
                response.setPaymentMethods(event.getPaymentsList());
                response.setEventType(PickPaymentFragEvent.SHOW_PAYMENTS);

                EventBus.getDefault().post(
                        response
                );
                break;

            case EventPaymentPresenter.MODEL_FAILURE_PAYMENTS_RESPONSE:
                response.setEventType(PickPaymentFragEvent.SHOW_ERROR_MESSAGE);

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
