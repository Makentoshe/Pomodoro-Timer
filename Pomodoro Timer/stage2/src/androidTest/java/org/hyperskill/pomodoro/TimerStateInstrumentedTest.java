package org.hyperskill.pomodoro;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TimerStateInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void textViewExist() {
        onView(ViewMatchers.withId(R.id.timerView)).perform(click());
    }

    @Test
    public void startTimer() throws InterruptedException {
        onView(withId(R.id.timerView)).check(matches(withText("00:03")));
        onView(withId(R.id.startButton)).perform(click());
        Thread.sleep(4000);
        onView(withId(R.id.timerView)).check(matches(withText("00:00")));
    }

    @Test
    public void resetTimer() throws InterruptedException {
        onView(withId(R.id.startButton)).perform(click());
        Thread.sleep(4000);
        onView(withId(R.id.timerView)).check(matches(withText("00:00")));
        onView(withId(R.id.resetButton)).perform(click());
        onView(withId(R.id.timerView)).check(matches(withText("00:03")));
    }

    @Test
    public void interruptTimer() throws InterruptedException {
        onView(withId(R.id.startButton)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.startButton)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.timerView)).check(matches(not(withText("00:00"))));
    }

}