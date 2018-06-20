package com.mercado.libre.paymentapp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;

import com.mercado.libre.paymentapp.mvp.views.MainActivity;
import com.mercado.libre.paymentapp.mvp.views.fragments.PickPaymentFragment;
import com.mercado.libre.paymentapp.utils.pojoModels.PaymentMethodPojo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.mercado.libre.paymentapp.MaterialSpinnerErrorText.hasMaterialSpinnerErrorText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.mock;

public class PickBankFragmentInstrumented {
    MainActivity mActivity;

    @Rule
    public ActivityTestRule rule = new ActivityTestRule(MainActivity.class,
            true, true);

    @Before
    public void setup(){
        mActivity = (MainActivity)rule.getActivity();
        mActivity.runOnUiThread(new Runnable() {

            public void run() {
                Bundle bundle = new Bundle();
                bundle.putInt("amount", 150000);
                bundle.putString("paymentId", "visa");

                mActivity.getNavController().navigate(R.id.pickBankFragment, bundle);
            }
        });

    }

    @Test
    public void checkIf_PickBankFragment_IsVisible() throws Exception{

        onView(withId(R.id.spnBank)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIf_SelectedValueIsSaved() throws Exception{
        PaymentMethodPojo pojo = mock(PaymentMethodPojo.class);

        String selectionText = "Banco Galicia";

        onView(withId(R.id.spnBank)).perform(click());
        onView(withText(selectionText)).perform(click());

        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withId(R.id.spnBank)).check(matches(withSpinnerText(containsString(selectionText))));

        onView(withId(R.id.fabNext)).perform(click());
        onView(withId(R.id.tbIcon)).perform(click());

        onView(withId(R.id.spnBank)).check(matches(withSpinnerText(containsString(selectionText))));
    }

    @Test
    public void checkNoPickedValueErrorText() throws Exception{
        onView(withId(R.id.fabNext)).perform(click());
        onView(withId(R.id.spnBank))
                .check(matches(hasMaterialSpinnerErrorText("Debe seleccionar un banco")));
    }


}
