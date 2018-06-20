package com.mercado.libre.paymentapp;

import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import fr.ganfra.materialspinner.MaterialSpinner;

public class MaterialSpinnerErrorText {
    public static Matcher<View> hasMaterialSpinnerErrorText(final String expectedErrorText) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof MaterialSpinner)) {
                    return false;
                }

                CharSequence error = ((MaterialSpinner) view).getError();

                if (error == null) {
                    return false;
                }

                String hint = error.toString();

                return expectedErrorText.equals(hint);
            }

            @Override
            public void describeTo(Description description) {
            }
        };
    }
}
