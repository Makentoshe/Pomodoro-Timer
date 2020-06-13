package org.hyperskill.pomodoro;

import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * If startButton id does not exist - the compilation will be failed,
     * so it's is a good point to check view visibility
     */
    @Test
    public void testShouldCheckStartButtonExist() {
        onView(withId(R.id.startButton)).check(matches(isDisplayed()));
    }

    /**
     * If resetButton id does not exist - the compilation will be failed,
     * so it's is a good point to check view visibility
     */
    @Test
    public void testShouldCheckResetButtonExist() {
        onView(withId(R.id.resetButton)).check(matches(isDisplayed()));
    }

    /**
     * If textView id does not exist - the compilation will be failed,
     * so it's is a good point to check view visibility
     */
    @Test
    public void testShouldCheckTextViewExist() {
        onView(withId(R.id.textView)).check(matches(isDisplayed()));
    }

    @Test
    public void testShouldCheckResetButtonText() {
        onView(withId(R.id.resetButton)).check(matches(withText("Reset")));
    }

    @Test
    public void testShouldCheckStartButtonText() {
        onView(withId(R.id.startButton)).check(matches(withText("Start")));
    }

}
