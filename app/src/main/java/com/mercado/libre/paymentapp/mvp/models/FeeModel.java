package com.mercado.libre.paymentapp.mvp.models;

import com.mercado.libre.paymentapp.utils.ApiInterface;
import com.mercado.libre.paymentapp.utils.HttpCode;
import com.mercado.libre.paymentapp.utils.events.fees.EventFeeModel;
import com.mercado.libre.paymentapp.utils.events.fees.EventFeePresenter;
import com.mercado.libre.paymentapp.utils.events.payment.EventPaymentModel;
import com.mercado.libre.paymentapp.utils.events.payment.EventPaymentPresenter;
import com.mercado.libre.paymentapp.utils.pojoModels.PaymentMethodPojo;
import com.mercado.libre.paymentapp.utils.pojoModels.ResponseFeesPojo;

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

public class FeeModel {
    private static final FeeModel mInstance = new FeeModel();

    public static FeeModel getInstance() {
        return mInstance;
    }

    private FeeModel() {
        EventBus.getDefault().register(this);
    }

    private void getFeesByAmount(ApiInterface service, String publicKey,
                                 int amount, String paymentId, String issuerId){
        final EventFeePresenter eventPresenter = new EventFeePresenter(
                EventFeePresenter.MODEL_SUCCES_FEES_RESPONSE);

        service.getFees(publicKey, amount, paymentId, issuerId)
        .enqueue(new Callback<ArrayList<ResponseFeesPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseFeesPojo>> call, Response<ArrayList<ResponseFeesPojo>> response) {
                switch (response.code()){

                    case HttpCode.CODE_200_SUCCESSFUL:
                    case HttpCode.CODE_201_SUCCESSFUL:
                        eventPresenter.setFeesList(response.body());
                        EventBus.getDefault().post(eventPresenter);
                        break;

                    case HttpCode.CODE_500_SERVER_ERROR:
                        eventPresenter.setEventType(EventPaymentPresenter.MODEL_FAILURE_PAYMENTS_RESPONSE);
                        EventBus.getDefault().post(eventPresenter);
                        break;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseFeesPojo>> call, Throwable t) {
                eventPresenter.setEventType(EventPaymentPresenter.MODEL_FAILURE_PAYMENTS_RESPONSE);
                EventBus.getDefault().post(eventPresenter);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(EventFeeModel evnt){
        getFeesByAmount(
                evnt.getService(),
                evnt.getPublicKey(),
                evnt.getAmount(),
                evnt.getPaymentId(),
                evnt.getIssuerId()
        );
    }
}
