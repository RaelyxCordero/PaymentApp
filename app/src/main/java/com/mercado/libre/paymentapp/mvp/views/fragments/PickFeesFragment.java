package com.mercado.libre.paymentapp.mvp.views.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mercado.libre.paymentapp.R;
import com.mercado.libre.paymentapp.utils.events.views.MainActivityEvent;

import org.greenrobot.eventbus.EventBus;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by raelyx on 15/06/18.
 */

public class PickFeesFragment extends Fragment {


    @BindView(R.id.spnFees)
    MaterialSpinner spnFees;
    @BindView(R.id.fabDone)
    FloatingActionButton fabDone;

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

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.fabDone)
    public void onViewClicked() {
        EventBus.getDefault().post(new MainActivityEvent(MainActivityEvent.NEXT_PRESSED));
        Navigation.findNavController(fabDone).navigate(R.id.addAmountFragment);
    }
}
