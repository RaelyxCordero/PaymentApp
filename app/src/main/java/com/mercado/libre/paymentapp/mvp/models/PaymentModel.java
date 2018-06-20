package com.mercado.libre.paymentapp.mvp.models;

import android.util.Log;

import com.mercado.libre.paymentapp.utils.ApiInterface;
import com.mercado.libre.paymentapp.utils.HttpCode;
import com.mercado.libre.paymentapp.utils.events.models.EventPaymentModel;
import com.mercado.libre.paymentapp.utils.events.presenters.EventPaymentPresenter;
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

    /* SIGLETON PATTERN METHODS
     * */
    private static final PaymentModel mInstance = new PaymentModel();

    public static PaymentModel getInstance() {
        return mInstance;
    }

    private PaymentModel() {
        EventBus.getDefault().register(this);
    }

    /* CALL SERVICE, get all payments methods
     * */
    private void getPayments(ApiInterface service, String publicKey){
        final EventPaymentPresenter eventPresenter =
                new EventPaymentPresenter(EventPaymentPresenter.MODEL_SUCCES_PAYMENTS_RESPONSE);
        Log.e("TAG", service.getPaymentMethod(publicKey).request().url().toString());
        service.getPaymentMethod(publicKey)
        .enqueue(new Callback<ArrayList<PaymentMethodPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<PaymentMethodPojo>> call,
                                   Response<ArrayList<PaymentMethodPojo>> response) {

                switch (response.code()){

                    case HttpCode.CODE_200_SUCCESSFUL:
                    case HttpCode.CODE_201_SUCCESSFUL:
                        //if successful, return list of paymentsMethods and post it in event
                        //to presenter (EventPaymentPresenter)
                        eventPresenter.setPaymentsList(response.body());
                        EventBus.getDefault().post(eventPresenter);
                        break;

                    default:
                        //else, return code and message to post an event, and presenter
                        //send dialog text on event to view depending of response code
                        eventPresenter.setResponseCode(response.code());
                        eventPresenter.setResponseMessage(response.message());
                        eventPresenter.setEventType(EventPaymentPresenter.MODEL_FAILURE_PAYMENTS_RESPONSE);
                        EventBus.getDefault().post(eventPresenter);
                        break;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PaymentMethodPojo>> call, Throwable t) {
                //onFailure, return code and message to post an event and presenter
                //send dialog text on event to view in case of code CODE_NO_RESPONSE_ERROR
                eventPresenter.setResponseCode(HttpCode.CODE_NO_RESPONSE_ERROR);
                eventPresenter.setResponseMessage(t.getMessage());
                eventPresenter.setEventType(EventPaymentPresenter.MODEL_FAILURE_PAYMENTS_RESPONSE);
                EventBus.getDefault().post(eventPresenter);
            }
        });

    }

    //EXECUTE getPayments() if EventPaymentModel is posted
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(EventPaymentModel evnt){
        getPayments(evnt.getService(),
                evnt.getPublicKey());
    }
}
