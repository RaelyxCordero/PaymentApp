package com.mercado.libre.paymentapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mercado.libre.paymentapp.mvp.views.MainActivity;
import com.mercado.libre.paymentapp.mvp.views.fragments.AddAmountFragment;
import com.mercado.libre.paymentapp.mvp.views.fragments.PickPaymentFragment;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static org.hamcrest.Matchers.allOf;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class AddAmountFragmentInstrumented {
    MainActivity mActivity;

    @Rule
    public ActivityTestRule rule = new ActivityTestRule(MainActivity.class,
            true, true);

    @Test
    public void checkAmountErrorText() throws Exception{
        onView(withId(R.id.tvAmount)).perform(typeText("0"));
        onView(withId(R.id.tvAmount)).perform(closeSoftKeyboard());
        onView(withId(R.id.fabNext)).perform(click());
        onView(withId(R.id.tvAmount))
                .check(matches(hasErrorText("El monto debe ser mayor a 0 y menor a 250.000")));

        onView(withId(R.id.tvAmount)).perform(clearText());
        onView(withId(R.id.tvAmount)).perform(typeText("250001"));
        onView(withId(R.id.tvAmount)).perform(closeSoftKeyboard());
        onView(withId(R.id.fabNext)).perform(click());
        onView(withId(R.id.tvAmount))
                .check(matches(hasErrorText("El monto debe ser mayor a 0 y menor a 250.000")));

        onView(withId(R.id.tvAmount)).perform(clearText());
        onView(withId(R.id.tvAmount)).perform(typeText("0.5"));
        onView(withId(R.id.tvAmount)).perform(closeSoftKeyboard());
        onView(withId(R.id.fabNext)).perform(click());
        onView(withId(R.id.tvAmount))
                .check(matches(hasErrorText("Ingrese un monto entero")));

        onView(withId(R.id.tvAmount)).perform(clearText());
        onView(withId(R.id.tvAmount)).perform(closeSoftKeyboard());
        onView(withId(R.id.fabNext)).perform(click());
        onView(withId(R.id.tvAmount))
                .check(matches(hasErrorText("Debe Agregar un monto")));
    }

    @Test
    public void checkTypedValueSaved() throws Exception{
        onView(withId(R.id.tvAmount)).perform(clearText());
        onView(withId(R.id.tvAmount)).perform(typeText("150000"));
        onView(withId(R.id.tvAmount)).perform(closeSoftKeyboard());
        onView(withId(R.id.fabNext)).perform(click());

        onView(withId(R.id.tbIcon)).perform(click());
        onView(withId(R.id.tvAmount)).check(matches(withText("150000")));

        onView(withId(R.id.tvAmount)).perform(clearText());
        onView(withId(R.id.tvAmount)).perform(typeText("150000"));
        onView(withId(R.id.tvAmount)).perform(closeSoftKeyboard());

        rule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        onView(withId(R.id.tvAmount)).check(matches(withText("150000")));

        rule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        onView(withId(R.id.tvAmount)).check(matches(withText("150000")));
    }

    @Test
    public void checkIf_DialogPaymentFinished_IsShowed(){
        final int amount = 150000;
        final String paymentName = "Visa";
        final String bankName = "Banco Galicia";
        final String payerCosts = "6 cuotas de $ 1.936,83 ($ 11.621,00)";

        mActivity = (MainActivity)rule.getActivity();
        mActivity.runOnUiThread(new Runnable() {

            public void run() {
                Bundle bundle = new Bundle();
                bundle.putInt("amount", amount);
                bundle.putString("paymentName", paymentName);
                bundle.putString("bankName", bankName);
                bundle.putString("payerCosts", payerCosts);

                mActivity.getNavController().navigate(R.id.addAmountFragment, bundle);
            }
        });

        String message = "Se ha realizado exitosamente el pago por: " + amount +"CLP"
                + "\n usando: " + paymentName
                + "\n con el banco: " + bankName
                + "\n siendo: " + payerCosts;

        onView(withText(message)).check(matches(isDisplayed()));
    }
}
