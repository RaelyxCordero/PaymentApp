package com.mercado.libre.paymentapp.mvp.views.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mercado.libre.paymentapp.R;
import com.mercado.libre.paymentapp.utils.pojoModels.BancoPojo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PickBankSpinnerAdapter extends CustomSpinnerAdapter {
    ArrayList<BancoPojo> bancoPojos;


    public PickBankSpinnerAdapter(Context applicationContext, ArrayList<BancoPojo> bancoPojos) {
        super(applicationContext, bancoPojos);
        this.bancoPojos = bancoPojos;
    }

    @Override
    public BancoPojo getItem(int i) {
        return bancoPojos.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.custom_spinner_item, null);

        TextView names = (TextView) view.findViewById(R.id.textView);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);

        Picasso.get()
                .load(bancoPojos.get(i).getThumbnail())
                .placeholder(R.drawable.ic_round_hourglass)
                .error(R.drawable.ic_image_error)
                .into(icon);

        names.setText(bancoPojos.get(i).getName());

        return view;
    }
}
