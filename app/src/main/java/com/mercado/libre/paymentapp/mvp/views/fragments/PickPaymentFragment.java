package com.mercado.libre.paymentapp.mvp.views.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mercado.libre.paymentapp.R;
import com.mercado.libre.paymentapp.mvp.views.CustomSpinnerAdapter;
import com.mercado.libre.paymentapp.utils.events.payment.EventPaymentPresenter;
import com.mercado.libre.paymentapp.utils.events.views.MainActivityEvent;
import com.mercado.libre.paymentapp.utils.events.views.PickPaymentFragEvent;
import com.mercado.libre.paymentapp.utils.pojoModels.PaymentMethodPojo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by raelyx on 15/06/18.
 */

public class PickPaymentFragment extends Fragment implements AdapterView.OnItemSelectedListener{


    @BindView(R.id.spnPayment)
    MaterialSpinner spnPayment;
    @BindView(R.id.fabNext)
    FloatingActionButton fabNext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_payment,
                container, false);

        ButterKnife.bind(this, view);

        spnPayment.setOnItemSelectedListener(this);
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new EventPaymentPresenter(EventPaymentPresenter.UI_GET_PAYMENTS));


        return view;
    }

    public void loadSpinner(ArrayList<PaymentMethodPojo> paymentMethods){
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getContext(), paymentMethods);
        spnPayment.setAdapter(customSpinnerAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.fabNext)
    public void onViewClicked() {
        EventBus.getDefault().post(new MainActivityEvent(MainActivityEvent.NEXT_PRESSED));
        Navigation.findNavController(fabNext).navigate(R.id.pickBankFragment);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(), position+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PickPaymentFragEvent event){
        switch (event.getEventType()){

            case PickPaymentFragEvent.SHOW_PAYMENTS:
                loadSpinner(event.getPaymentMethods());
                break;

            case PickPaymentFragEvent.SHOW_ERROR_MESSAGE:
                Toast.makeText(getContext(), "error en la consulta", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
