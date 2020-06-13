package org.hyperskill.pomodoro;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void textViewExist() {
        onView(withId(R.id.timerView)).perform(click());
    }

    @Test
    public void startButtonExist() {
        onView(withId(R.id.startButton)).perform(click());
    }

    @Test
    public void resetButtonExist() {
        onView(withId(R.id.resetButton)).perform(click());
    }

}
