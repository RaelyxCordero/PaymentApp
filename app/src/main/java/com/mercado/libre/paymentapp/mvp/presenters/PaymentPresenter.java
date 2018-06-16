package com.mercado.libre.paymentapp.mvp.presenters;

import com.mercado.libre.paymentapp.mvp.models.PaymentModel;
import com.mercado.libre.paymentapp.utils.ApiInterface;
import com.mercado.libre.paymentapp.utils.events.payment.EventPaymentModel;
import com.mercado.libre.paymentapp.utils.events.payment.EventPaymentPresenter;
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

        switch (event.getEventType()){
            case EventPaymentPresenter.UI_GET_PAYMENTS:
                EventBus.getDefault().post(
                        new EventPaymentModel(PUBLIC_KEY, service)
                );
                break;

            case EventPaymentPresenter.MODEL_SUCCES_PAYMENTS_RESPONSE:
                EventBus.getDefault().post(
                        new PickPaymentFragEvent(PickPaymentFragEvent.SHOW_PAYMENTS, event.getPaymentsList())
                );
                break;

            case EventPaymentPresenter.MODEL_FAILURE_PAYMENTS_RESPONSE:
                break;

        }
    }
}
