package com.mercado.libre.paymentapp.mvp.models;

import android.util.Log;

import com.mercado.libre.paymentapp.utils.ApiInterface;
import com.mercado.libre.paymentapp.utils.HttpCode;
import com.mercado.libre.paymentapp.utils.events.payment.EventPaymentModel;
import com.mercado.libre.paymentapp.utils.events.payment.EventPaymentPresenter;
import com.mercado.libre.paymentapp.utils.pojoModels.PaymentMethodPojo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raelyx on 15/06/18.
 */

public class PaymentModel {
    private static final PaymentModel mInstance = new PaymentModel();

    public static PaymentModel getInstance() {
        return mInstance;
    }

    private PaymentModel() {
        EventBus.getDefault().register(this);
    }

    private void getPayments(ApiInterface service, String publicKey){
        final EventPaymentPresenter eventPresenter =
                new EventPaymentPresenter(EventPaymentPresenter.MODEL_SUCCES_PAYMENTS_RESPONSE);
        Log.e("TAG", service.getPaymentMethod().request().url().toString());
        service.getPaymentMethod()
        .enqueue(new Callback<ArrayList<PaymentMethodPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<PaymentMethodPojo>> call,
                                   Response<ArrayList<PaymentMethodPojo>> response) {

                switch (response.code()){

                    case HttpCode.CODE_200_SUCCESSFUL:
                    case HttpCode.CODE_201_SUCCESSFUL:
                        eventPresenter.setPaymentsList(response.body());
                        EventBus.getDefault().post(eventPresenter);
                        break;

                    case HttpCode.CODE_500_SERVER_ERROR:
                        eventPresenter.setEventType(EventPaymentPresenter.MODEL_FAILURE_PAYMENTS_RESPONSE);
                        EventBus.getDefault().post(eventPresenter);
                        break;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PaymentMethodPojo>> call, Throwable t) {
                eventPresenter.setEventType(EventPaymentPresenter.MODEL_FAILURE_PAYMENTS_RESPONSE);
                EventBus.getDefault().post(eventPresenter);
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(EventPaymentModel evnt){
        getPayments(evnt.getService(),
                evnt.getPublicKey());
    }
}
