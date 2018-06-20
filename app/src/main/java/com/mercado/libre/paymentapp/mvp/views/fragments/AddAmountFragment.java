package com.mercado.libre.paymentapp.mvp.views.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mercado.libre.paymentapp.R;
import com.mercado.libre.paymentapp.mvp.views.viewModels.MainActivityViewModel;
import com.mercado.libre.paymentapp.utils.events.views.MainActivityEvent;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by raelyx on 15/06/18.
 */

public class AddAmountFragment extends Fragment implements Validator.ValidationListener{

    @NotEmpty(message = "Debe Agregar un monto")
    @BindView(R.id.tvAmount)
    TextInputEditText tvAmount;
    @BindView(R.id.fabNext)
    FloatingActionButton fabNext;
    private Validator validator;
    private MainActivityViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_amount,
                container, false);

        ButterKnife.bind(this, view);

        validator = new Validator(this);
        validator.setValidationListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //if getArguments has an amount, then show the dialog of the PaymentFinished
        if (getArguments().getInt("amount") != 0 && !mViewModel.isDialogPaymentShowed()){

            String message = "Se ha realizado exitosamente el pago por: " + getArguments().getInt("amount") +"CLP"
                    + "\n usando: " + getArguments().getString("paymentName")
                    + "\n con el banco: " + getArguments().getString("bankName")
                    + "\n siendo: " + getArguments().getString("payerCosts")
                    ;
            showDialogPayFinishedMessage(R.string.success, message);

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
    public void onValidationSucceeded() {
        try{
            int amount = Integer.valueOf(tvAmount.getText().toString());
            if (amount > 0 && amount <= 250000){
                Bundle bundle = new Bundle();
                bundle.putInt("amount", amount);

                //navigate to pickPaymentFragment
                EventBus.getDefault().post(new MainActivityEvent(MainActivityEvent.NEXT_PRESSED));
                Navigation.findNavController(fabNext).navigate(R.id.pickPaymentFragment, bundle);
            }else {
                tvAmount.setError("El monto debe ser mayor a 0 y menor a 250.000");
            }
        }catch (NumberFormatException e){
            tvAmount.setError("Ingrese un monto entero");
        }


    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            // Display error messages ;)
            if (view instanceof TextInputEditText) {
                ((TextInputEditText) view).setError(message);

            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showDialogPayFinishedMessage(int tittle, String message){
        new MaterialDialog.Builder(getContext())
                .title(tittle)
                .content(message)
                .positiveText(R.string.accept)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        mViewModel.setDialogPaymentShowed(true);

                    }
                })
                .show();
    }
}
