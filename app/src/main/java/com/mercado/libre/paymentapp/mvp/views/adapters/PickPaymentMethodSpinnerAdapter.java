package com.mercado.libre.paymentapp.mvp.views.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mercado.libre.paymentapp.R;
import com.mercado.libre.paymentapp.utils.pojoModels.BancoPojo;
import com.mercado.libre.paymentapp.utils.pojoModels.PaymentMethodPojo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PickPaymentMethodSpinnerAdapter extends CustomSpinnerAdapter {
    ArrayList<PaymentMethodPojo> methodPojo;


    public PickPaymentMethodSpinnerAdapter(Context applicationContext, ArrayList<PaymentMethodPojo> methodPojo) {
        super(applicationContext, methodPojo);
        this.methodPojo = methodPojo;
    }

    @Override
    public PaymentMethodPojo getItem(int i) {
        return methodPojo.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.custom_spinner_item, null);

        TextView names = (TextView) view.findViewById(R.id.textView);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);

        Picasso.get()
                .load(methodPojo.get(i).getThumbnail())
                .placeholder(R.drawable.ic_round_hourglass)
                .error(R.drawable.ic_image_error)
                .into(icon);

        names.setText(methodPojo.get(i).getName());

        return view;
    }
}
