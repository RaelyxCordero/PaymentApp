package com.mercado.libre.paymentapp.mvp.models;

import android.util.Log;

import com.mercado.libre.paymentapp.utils.ApiInterface;
import com.mercado.libre.paymentapp.utils.HttpCode;
import com.mercado.libre.paymentapp.utils.events.models.EventFeeModel;
import com.mercado.libre.paymentapp.utils.events.presenters.EventFeePresenter;
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

    /* SIGLETON PATTERN METHODS
     * */
    private static final FeeModel mInstance = new FeeModel();

    public static FeeModel getInstance() {
        return mInstance;
    }

    private FeeModel() {
        EventBus.getDefault().register(this);
    }


    /* CALL SERVICE, get all fees by an amount, a paymentId and issuerId
     * */
    private void getFeesByAmount(ApiInterface service, String publicKey,
                                 int amount, String paymentId, String issuerId){
        final EventFeePresenter eventPresenter = new EventFeePresenter(
                EventFeePresenter.MODEL_SUCCES_FEES_RESPONSE);

        Log.e("TAG", service.getFees(publicKey, amount, paymentId, issuerId).request().url().toString());
        service.getFees(publicKey, amount, paymentId, issuerId)
        .enqueue(new Callback<ArrayList<ResponseFeesPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseFeesPojo>> call,
                                   Response<ArrayList<ResponseFeesPojo>> response) {
                switch (response.code()){

                    case HttpCode.CODE_200_SUCCESSFUL:
                    case HttpCode.CODE_201_SUCCESSFUL:

                        //if successful, return list of feesResponse and post it in event
                        //to presenter (EventFeePresenter)
                        eventPresenter.setFeesList(response.body());
                        EventBus.getDefault().post(eventPresenter);
                        break;

                    default:
                        //else, return code and message to post an event, and presenter
                        //send dialog text on event to view depending of response code
                        eventPresenter.setResponseCode(response.code());
                        eventPresenter.setResponseMessage(response.message());
                        eventPresenter.setEventType(EventFeePresenter.MODEL_FAILURE_FEES_RESPONSE);
                        EventBus.getDefault().post(eventPresenter);
                        break;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseFeesPojo>> call, Throwable t) {
                //onFailure, return code and message to post an event and presenter
                //send dialog text on event to view in case of code CODE_NO_RESPONSE_ERROR
                eventPresenter.setResponseCode(HttpCode.CODE_NO_RESPONSE_ERROR);
                eventPresenter.setResponseMessage(t.getMessage());
                eventPresenter.setEventType(EventFeePresenter.MODEL_FAILURE_FEES_RESPONSE);
                EventBus.getDefault().post(eventPresenter);
            }
        });
    }

    //EXECUTE getFeesByAmount() if EventFeeModel is posted
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
