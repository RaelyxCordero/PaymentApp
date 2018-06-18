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
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mercado.libre.paymentapp.R;
import com.mercado.libre.paymentapp.mvp.views.CustomSpinnerAdapter;
import com.mercado.libre.paymentapp.utils.events.presenters.EventBankPresenter;
import com.mercado.libre.paymentapp.utils.events.views.MainActivityEvent;
import com.mercado.libre.paymentapp.utils.events.views.PickBankFragEvent;
import com.mercado.libre.paymentapp.utils.pojoModels.BancoPojo;
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

public class PickBankFragment extends Fragment implements AdapterView.OnItemSelectedListener,
        Validator.ValidationListener, MaterialDialog.SingleButtonCallback {
    //TODO: Onsave instance selected item and save instance on back
    @Select(message = "Debe seleccionar un banco")
    @BindView(R.id.spnBank)
    MaterialSpinner spnBank;
    @BindView(R.id.fabNext)
    FloatingActionButton fabNext;
    private Validator validator;
    private MaterialDialog materialProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_bank,
                container, false);

        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        progressbarVisibility(true);
        spnBank.setOnItemSelectedListener(this);

        EventBankPresenter event = new EventBankPresenter(
                EventBankPresenter.UI_GET_BANKS,
                getArguments().getString("paymentId")
        );

        EventBus.getDefault().post(event);

        validator = new Validator(this);
        validator.setValidationListener(this);
        return view;
    }

    public void loadSpinner(ArrayList<BancoPojo> bancoPojos){
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getContext(), bancoPojos);
        spnBank.setAdapter(customSpinnerAdapter);
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
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onValidationSucceeded() {
        BancoPojo pojo = (BancoPojo) spnBank.getAdapter()
                .getItem(spnBank.getSelectedItemPosition());

        Log.e("TAG", pojo.toString());

        Bundle bundle = new Bundle();
        bundle.putInt("amount", getArguments().getInt("amount"));
        bundle.putString("paymentId", getArguments().getString("paymentId"));
        bundle.putString("bankId", pojo.getId());

        Log.e("TAG", bundle.getString("paymentId") + ", " + bundle.getInt("amount")
                + ", " + bundle.getString("bankId"));

        EventBus.getDefault().post(new MainActivityEvent(MainActivityEvent.NEXT_PRESSED));
        Navigation.findNavController(fabNext).navigate(R.id.pickFeesFragment, bundle);
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

    private void showDialogProblemsMessage(int tittle, int message){
        progressbarVisibility(false);
        new MaterialDialog.Builder(getContext())
                .title(tittle)
                .content(message)
                .positiveText(R.string.accept)
                .onAny(this)
                .show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PickBankFragEvent event){
        switch (event.getEventType()){

            case PickBankFragEvent.SHOW_BANKS:
                loadSpinner(event.getBanks());
                break;

            case PickBankFragEvent.SHOW_ERROR_MESSAGE:
                showDialogProblemsMessage(R.string.error, event.getCustomMessage());
                Log.e("TAG", event.getResponseCode() + " " + event.getResponseMessage());
                break;
            case PickBankFragEvent.SHOW_EMPTY_LIST_MESSAGE:
                showDialogProblemsMessage(R.string.information, event.getCustomMessage());
                break;
        }
    }

    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        getActivity().onBackPressed();
    }
}
