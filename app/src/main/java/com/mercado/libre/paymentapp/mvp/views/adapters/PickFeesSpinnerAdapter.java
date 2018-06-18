package com.mercado.libre.paymentapp.mvp.views.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mercado.libre.paymentapp.R;
import com.mercado.libre.paymentapp.utils.pojoModels.PayerCost;
import com.mercado.libre.paymentapp.utils.pojoModels.PaymentMethodPojo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PickFeesSpinnerAdapter extends CustomSpinnerAdapter {
    ArrayList<PayerCost> methodPojo;


    public PickFeesSpinnerAdapter(Context applicationContext, ArrayList<PayerCost> methodPojo) {
        super(applicationContext, methodPojo);
        this.methodPojo = methodPojo;
    }

    @Override
    public PayerCost getItem(int i) {
        return methodPojo.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.custom_spinner_item, null);

        TextView names = (TextView) view.findViewById(R.id.textView);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);

        Picasso.get()
                .load(R.drawable.ic_dollar)
                .placeholder(R.drawable.ic_round_hourglass)
                .error(R.drawable.ic_image_error)
                .into(icon);

        names.setText(methodPojo.get(i).getRecommendedMessage());

        return view;
    }
}
