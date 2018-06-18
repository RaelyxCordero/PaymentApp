package com.mercado.libre.paymentapp.mvp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mercado.libre.paymentapp.R;
import com.mercado.libre.paymentapp.utils.pojoModels.BancoPojo;
import com.mercado.libre.paymentapp.utils.pojoModels.PayerCost;
import com.mercado.libre.paymentapp.utils.pojoModels.PaymentMethodPojo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomSpinnerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList objects;
    protected LayoutInflater inflater;

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

        TextView names = (TextView) view.findViewById(R.id.textView);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);

        return view;
    }

}
