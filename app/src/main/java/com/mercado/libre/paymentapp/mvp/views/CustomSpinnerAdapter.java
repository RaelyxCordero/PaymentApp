package com.mercado.libre.paymentapp.mvp.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mercado.libre.paymentapp.R;
import com.mercado.libre.paymentapp.utils.pojoModels.BancoPojo;
import com.mercado.libre.paymentapp.utils.pojoModels.PaymentMethodPojo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomSpinnerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList objects;

    private LayoutInflater inflater;

    public CustomSpinnerAdapter(Context applicationContext, ArrayList objects) {
        this.context = applicationContext;
        this.objects = objects;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.custom_spinner_item, null);

        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);

        if (objects.get(i) instanceof BancoPojo){
            BancoPojo pojo = (BancoPojo) objects.get(i);
            Picasso.get()
                    .load(pojo.getThumbnail())
                    .placeholder(R.drawable.ic_round_hourglass)
                    .error(R.drawable.ic_image_error)
                    .into(icon);

            names.setText(pojo.getName());

        }else if (objects.get(i) instanceof PaymentMethodPojo){
            PaymentMethodPojo pojo = (PaymentMethodPojo) objects.get(i);
            Picasso.get()
                    .load(pojo.getThumbnail())
                    .placeholder(R.drawable.ic_round_hourglass)
                    .error(R.drawable.ic_image_error)
                    .into(icon);

            names.setText(pojo.getName());
        }


        return view;
    }
}
