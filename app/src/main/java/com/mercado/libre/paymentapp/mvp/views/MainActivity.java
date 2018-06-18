package com.mercado.libre.paymentapp.mvp.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.BaseMenuPresenter;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mercado.libre.paymentapp.R;
import com.mercado.libre.paymentapp.mvp.presenters.BankPresenter;
import com.mercado.libre.paymentapp.mvp.presenters.FeePresenter;
import com.mercado.libre.paymentapp.mvp.presenters.PaymentPresenter;
import com.mercado.libre.paymentapp.mvp.views.viewModels.MainActivityViewModel;
import com.mercado.libre.paymentapp.utils.events.views.MainActivityEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements NavHost, NavController.OnNavigatedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.tbIcon)
    ImageView tbIcon;
    @BindView(R.id.tbTitle)
    TextView tbTitle;
    private boolean tbBackStatus = false;
    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getNavController().addOnNavigatedListener(this);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        PaymentPresenter paymentPresenter = PaymentPresenter.getInstance();
        BankPresenter bankPresenter = BankPresenter.getInstance();
        FeePresenter feePresenter = FeePresenter.getInstance();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        tbBackStatus = mViewModel.isTbStatus();
        if (tbBackStatus)
            tbIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
        else
            tbIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.tbIcon)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (tbBackStatus){
            super.onBackPressed();
        }else {
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.container).navigateUp();
    }

    @NonNull
    @Override
    public NavController getNavController() {
        return Navigation.findNavController(this, R.id.container);
    }

    @Subscribe
    public void onEvent(MainActivityEvent event){
        switch (event.getEventType()){

            case MainActivityEvent.BACK_PRESSED:
                onViewClicked();
                break;

            case MainActivityEvent.NEXT_PRESSED:
                tbBackStatus = true;
                mViewModel.setTbStatus(true);
                tbIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
                break;
        }
    }

    @Override
    public void onNavigated(@NonNull NavController controller, @NonNull NavDestination destination) {
        Log.e(TAG, "" + getNavController().getCurrentDestination().getId());
        Log.e(TAG, "" + R.id.addAmountFragment);

        if (getNavController().getCurrentDestination().getId() == R.id.addAmountFragment){
            tbBackStatus = false;
            if (mViewModel != null)
                mViewModel.setTbStatus(false);
            tbIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
            tbTitle.setText(getResources().getString(R.string.title_add_amount));

        } else if (getNavController().getCurrentDestination().getId() == R.id.pickPaymentFragment)
            tbTitle.setText(getResources().getString(R.string.title_pick_payment));

        else if (getNavController().getCurrentDestination().getId() == R.id.pickBankFragment)
            tbTitle.setText(getResources().getString(R.string.title_pick_bank));

        else if (getNavController().getCurrentDestination().getId() == R.id.pickFeesFragment)
            tbTitle.setText(getResources().getString(R.string.title_pick_fee));
    }
}
