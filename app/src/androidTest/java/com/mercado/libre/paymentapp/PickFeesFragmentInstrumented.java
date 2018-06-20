package com.mercado.libre.paymentapp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;

import com.mercado.libre.paymentapp.mvp.views.MainActivity;
import com.mercado.libre.paymentapp.utils.pojoModels.PaymentMethodPojo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.mercado.libre.paymentapp.MaterialSpinnerErrorText.hasMaterialSpinnerErrorText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.mock;

public class PickFeesFragmentInstrumented {
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
                bundle.putString("bankId", "288");

                mActivity.getNavController().navigate(R.id.pickFeesFragment, bundle);
            }
        });

    }

    @Test
    public void checkIf_PickBankFragment_IsVisible() throws Exception{

        onView(withId(R.id.spnFees)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIf_SelectedValueIsSaved() throws Exception{

        String selectionText = "1 cuota de $ 150.000,00 ($ 150.000,00)";

        onView(withId(R.id.spnFees)).perform(click());
        onView(withText(selectionText)).perform(click());

        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withText(selectionText)).check(matches(
                allOf(isDisplayed(), isDescendantOfA(withId(R.id.spnFees)))
        ));

        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        onView(withText(selectionText)).check(matches(
                allOf(isDisplayed(), isDescendantOfA(withId(R.id.spnFees)))
        ));
    }

    @Test
    public void checkNoPickedValueErrorText() throws Exception{
        onView(withId(R.id.fabDone)).perform(click());
        onView(withId(R.id.spnFees))
                .check(matches(hasMaterialSpinnerErrorText(
                        "Debe seleccionar una cantidad de cuotas")));
    }


}
