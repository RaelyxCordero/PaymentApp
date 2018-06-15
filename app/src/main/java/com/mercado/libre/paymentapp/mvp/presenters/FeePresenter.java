package com.mercado.libre.paymentapp.mvp.presenters;

import com.mercado.libre.paymentapp.mvp.models.FeeModel;
import com.mercado.libre.paymentapp.mvp.models.PaymentModel;
import com.mercado.libre.paymentapp.utils.ApiInterface;
import com.mercado.libre.paymentapp.utils.events.fees.EventFeeModel;
import com.mercado.libre.paymentapp.utils.events.fees.EventFeePresenter;
import com.mercado.libre.paymentapp.utils.events.payment.EventPaymentModel;
import com.mercado.libre.paymentapp.utils.events.payment.EventPaymentPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

        switch (event.getEventType()){
            case EventFeePresenter.UI_GET_FEE:

                EventBus.getDefault().post(
                        new EventFeeModel(PUBLIC_KEY, service,
                        event.getPaymentMethodId(), event.getIssuerId(), event.getAmount())
                );
                break;

            case EventFeePresenter.MODEL_SUCCES_FEES_RESPONSE:
                break;

            case EventFeePresenter.MODEL_FAILURE_FEES_RESPONSE:
                break;

        }
    }
}
