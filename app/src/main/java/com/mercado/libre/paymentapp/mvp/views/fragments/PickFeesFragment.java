package com.mercado.libre.paymentapp.mvp.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mercado.libre.paymentapp.R;
import com.mercado.libre.paymentapp.mvp.views.CustomSpinnerAdapter;
import com.mercado.libre.paymentapp.utils.events.presenters.EventFeePresenter;
import com.mercado.libre.paymentapp.utils.events.views.MainActivityEvent;
import com.mercado.libre.paymentapp.utils.events.views.PickFeeFragEvent;
import com.mercado.libre.paymentapp.utils.pojoModels.PayerCost;
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

public class PickFeesFragment extends Fragment implements AdapterView.OnItemSelectedListener,
        Validator.ValidationListener, MaterialDialog.SingleButtonCallback{
    //TODO: Onsave instance selected item and save instance on back

    @Select(message = "Debe seleccionar una cantidad de cuotas")
    @BindView(R.id.spnFees)
    MaterialSpinner spnFees;
    @BindView(R.id.fabDone)
    FloatingActionButton fabDone;
    private Validator validator;
    private MaterialDialog materialProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_fees,
                container, false);

        ButterKnife.bind(this, view);

        EventBus.getDefault().register(this);
        progressbarVisibility(true);
        spnFees.setOnItemSelectedListener(this);

        Log.e("TAG", getArguments().getString("paymentId") + ", " + getArguments().getInt("amount")
                + ", " + getArguments().getString("bankId"));

        EventFeePresenter event = new EventFeePresenter(
                EventFeePresenter.UI_GET_FEE,
                getArguments().getString("paymentId"),
                getArguments().getString("bankId"),
                getArguments().getInt("amount")
        );

        EventBus.getDefault().post(event);

        validator = new Validator(this);
        validator.setValidationListener(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    public void loadSpinner(ArrayList<PayerCost> payerCosts){
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getContext(), payerCosts);
        spnFees.setAdapter(customSpinnerAdapter);
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

    private void showDialogProblemsMessage(int tittle, int message){
        progressbarVisibility(false);
        new MaterialDialog.Builder(getContext())
                .title(tittle)
                .content(message)
                .positiveText(R.string.accept)
                .onAny(this)
                .show();
    }

    @OnClick(R.id.fabDone)
    public void onViewClicked() {
        validator.validate();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onValidationSucceeded() {
        EventBus.getDefault().post(new MainActivityEvent(MainActivityEvent.NEXT_PRESSED));
        Navigation.findNavController(fabDone).navigate(R.id.addAmountFragment);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PickFeeFragEvent event){
        switch (event.getEventType()){

            case PickFeeFragEvent.SHOW_FEES:
                loadSpinner(event.getPayerFees());
                break;

            case PickFeeFragEvent.SHOW_ERROR_MESSAGE:
                showDialogProblemsMessage(R.string.error, event.getCustomMessage());

                Log.e("TAG", event.getResponseCode() + " " + event.getResponseMessage());
                break;
            case PickFeeFragEvent.SHOW_EMPTY_LIST_MESSAGE:
                showDialogProblemsMessage(R.string.information, event.getCustomMessage());
                break;
        }
    }

    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        getActivity().onBackPressed();
    }
}
