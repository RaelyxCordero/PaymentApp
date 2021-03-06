package com.mercado.libre.paymentapp.mvp.views.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mercado.libre.paymentapp.R;
import com.mercado.libre.paymentapp.mvp.views.adapters.PickPaymentMethodSpinnerAdapter;
import com.mercado.libre.paymentapp.mvp.views.viewModels.PickSpinnerViewModel;
import com.mercado.libre.paymentapp.utils.events.presenters.EventPaymentPresenter;
import com.mercado.libre.paymentapp.utils.events.views.MainActivityEvent;
import com.mercado.libre.paymentapp.utils.events.views.PickPaymentFragEvent;
import com.mercado.libre.paymentapp.utils.pojoModels.PaymentMethodPojo;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Select;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by raelyx on 15/06/18.
 */

public class PickPaymentFragment extends Fragment implements AdapterView.OnItemSelectedListener,
        Validator.ValidationListener, MaterialDialog.SingleButtonCallback {
    @Select(message = "Debe seleccionar un medio de pago")
    @BindView(R.id.spnPayment)
    MaterialSpinner spnPayment;
    @BindView(R.id.fabNext)
    FloatingActionButton fabNext;
    private PickSpinnerViewModel mViewModel;
    private Validator validator;
    private MaterialDialog materialProgressDialog;
    private PaymentMethodPojo pojo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PickSpinnerViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_payment,
                container, false);

        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        spnPayment.setOnItemSelectedListener(this);

        if (mViewModel.getSpinnerList() == null){
            progressbarVisibility(true);

            //if no items, trigger an event to get it
            EventBus.getDefault().post(new EventPaymentPresenter(EventPaymentPresenter.UI_GET_PAYMENTS));
        }else {

            //else load saved items
            loadSpinner(mViewModel.getSpinnerList());
            spnPayment.setSelection(mViewModel.getSelectedSpinnerItem());
        }



        validator = new Validator(this);
        validator.setValidationListener(this);
        return view;
    }

    public void loadSpinner(ArrayList<PaymentMethodPojo> paymentMethods){
        PickPaymentMethodSpinnerAdapter customSpinnerAdapter =
                new PickPaymentMethodSpinnerAdapter(getContext(), paymentMethods);
        spnPayment.setAdapter(customSpinnerAdapter);
        progressbarVisibility(false);
    }

    public void progressbarVisibility(boolean visible) {
        if (materialProgressDialog == null) {
            materialProgressDialog = new MaterialDialog.Builder(getContext())
                    .title("")
                    .content(R.string.please_wait)
                    .progress(true, 0)
                    .cancelable(false)

                    .build();
        }

        if (visible) {
            materialProgressDialog = materialProgressDialog
                    .getBuilder()
                    .title("")
                    .content(R.string.please_wait)
                    .show();

        } else {
            materialProgressDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.fabNext)
    public void onViewClicked() {
        validator.validate();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != -1)
            pojo = (PaymentMethodPojo) parent.getItemAtPosition(position);
        mViewModel.setSelectedSpinnerItem(position + 1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //listen events PickPaymentFragEvent
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PickPaymentFragEvent event){
        switch (event.getEventType()){

            //if event type is SHOW_PAYMENTS, load the spinner and save it in ViewModel
            case PickPaymentFragEvent.SHOW_PAYMENTS:
                loadSpinner(event.getPaymentMethods());
                mViewModel.setSpinnerList(event.getPaymentMethods());
                break;

            //if event type is SHOW_ERROR_MESSAGE, show the
            //received custom message in error dialog
            // and log the response code and response message
            case PickPaymentFragEvent.SHOW_ERROR_MESSAGE:
                progressbarVisibility(false);

                new MaterialDialog.Builder(getContext())
                        .title(R.string.error)
                        .content(event.getCustomMessage())
                        .positiveText(R.string.accept)
                        .onAny(this)
                        .show();

                Log.e("TAG", event.getResponseCode() + " " + event.getResponseMessage());
                break;

        }
    }

    @Override
    public void onValidationSucceeded() {
        Bundle bundle = new Bundle();
        bundle.putInt("amount", getArguments().getInt("amount"));
        bundle.putString("paymentId", pojo.getId());
        bundle.putString("paymentName", pojo.getName());

        Log.e("TAG", bundle.getString("paymentId") + ", " + bundle.getInt("amount"));

        //navigate to pickBankFragment
        EventBus.getDefault().post(new MainActivityEvent(MainActivityEvent.NEXT_PRESSED));
        Navigation.findNavController(fabNext).navigate(R.id.pickBankFragment, bundle);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            // Display error messages ;)
            if (view instanceof MaterialSpinner) {
                ((MaterialSpinner) view).setError(message);

            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }


    //if dialog button is clicked get back into previous navigation node
    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        getActivity().onBackPressed();
    }
}
