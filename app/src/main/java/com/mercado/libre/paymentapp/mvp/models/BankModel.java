package com.mercado.libre.paymentapp.mvp.models;

import android.util.Log;

import com.mercado.libre.paymentapp.utils.ApiInterface;
import com.mercado.libre.paymentapp.utils.HttpCode;
import com.mercado.libre.paymentapp.utils.events.models.EventBankModel;
import com.mercado.libre.paymentapp.utils.events.presenters.EventBankPresenter;
import com.mercado.libre.paymentapp.utils.pojoModels.BancoPojo;

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

public class BankModel {
    private static final BankModel mInstance = new BankModel();

    public static BankModel getInstance() {
        return mInstance;
    }

    private BankModel() {
        EventBus.getDefault().register(this);
    }

    private void getBanksByPaymentId(ApiInterface service, String publicKey, String paymentId){
        final EventBankPresenter eventPresenter =
                new EventBankPresenter(EventBankPresenter.MODEL_SUCCES_BANK_RESPONSE);

        Log.e("TAG", service.getBankByPaymentMethodId(publicKey, paymentId).request().url().toString());
        service.getBankByPaymentMethodId(publicKey, paymentId)
        .enqueue(new Callback<ArrayList<BancoPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<BancoPojo>> call,
                                   Response<ArrayList<BancoPojo>> response) {

                switch (response.code()){

                    case HttpCode.CODE_200_SUCCESSFUL:
                    case HttpCode.CODE_201_SUCCESSFUL:

                        eventPresenter.setBanksList(response.body());
                        EventBus.getDefault().post(eventPresenter);
                        break;

                    default:
                        eventPresenter.setResponseCode(response.code());
                        eventPresenter.setResponseMessage(response.message());
                        eventPresenter.setEventType(EventBankPresenter.MODEL_FAILURE_BANK_RESPONSE);
                        EventBus.getDefault().post(eventPresenter);
                        break;
                }

            }

            @Override
            public void onFailure(Call<ArrayList<BancoPojo>> call, Throwable t) {
                eventPresenter.setResponseCode(HttpCode.CODE_NO_RESPONSE_ERROR);
                eventPresenter.setResponseMessage(t.getMessage());
                eventPresenter.setEventType(EventBankPresenter.MODEL_FAILURE_BANK_RESPONSE);
                EventBus.getDefault().post(eventPresenter);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(EventBankModel eventBank){
        getBanksByPaymentId(eventBank.getService(),
                eventBank.getPublicKey(),
                eventBank.getPaymentId());
    }
}
