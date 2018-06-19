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
    AddAmountFragment testedFragment;
    PickPaymentFragment testedFragment2;
    PickPaymentFragment spyTestedFragment2;

    @Rule
    public ActivityTestRule rule = new ActivityTestRule(MainActivity.class,
            true, true);

    @Before
    public void setup(){
        testedFragment = mock(AddAmountFragment.class);
        testedFragment2 = mock(PickPaymentFragment.class);
        spyTestedFragment2 = spy(testedFragment2);
    }

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
    public void checkTypedValueSaved(){
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
}
