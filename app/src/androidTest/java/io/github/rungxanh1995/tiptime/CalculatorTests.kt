package io.github.rungxanh1995.tiptime

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CalculatorTests {
    @get:Rule val activity = ActivityScenarioRule(MainActivity::class.java)


    /**
     * This test checks the default tip option of 20%
     * with rounded tip amount enabled by default,
     * and clicking on Calculate button
     */
    @Test
    fun calculate_20_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText("50.00"))

        onView(withId(R.id.calculate_button))
            .perform(click())

        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString("$10.00"))))
    }


    /**
     * This test tests the precision of NON-ROUNDED tip option for an 18% tip
     * and clicking on Calculate button
     */
    @Test
    fun calculate_18_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText("45.45"))

        onView(withId(R.id.option_18_percent))
            .perform(click())

        onView(withId(R.id.round_up_switch))
            .perform(click())

        onView(withId(R.id.calculate_button))
            .perform(click())

        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString("$8.18"))))
    }


    /**
     * This test checks the event listener of radio buttons for tip
     * by clicking the 15% option after the default 20%,
     * assumes the tip is rounded up,
     * also checks auto-displayed tip amount without clicking on Calculate button
     */
    @Test
    fun calculate_15_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText("27.25"))


        onView(withId(R.id.option_15_percent))
            .perform(click())

        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString("$5.00"))))
    }
}