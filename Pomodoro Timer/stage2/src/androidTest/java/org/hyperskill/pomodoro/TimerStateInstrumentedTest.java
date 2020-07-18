package org.hyperskill.pomodoro;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TimerStateInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testShouldCheckTimerInitialValue() {
        onView(withId(R.id.textView)).check(matches(withText("00:00")));
    }

    @Test
    public void testShouldStartCountOnStartButtonClick() throws InterruptedException {
        onView(withId(R.id.startButton)).perform(click());
        Thread.sleep(1100);
        onView(withId(R.id.textView)).check(matches(withText("00:01")));
    }

    @Test
    public void testShouldStopTimerAndResetCountOnResetButtonClick() throws InterruptedException {
        onView(withId(R.id.startButton)).perform(click());
        Thread.sleep(1100);
        onView(withId(R.id.resetButton)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("00:00")));
    }

    @Test
    public void testShouldContinueCountOnPressingStartButtonAgain() throws InterruptedException {
        testShouldStartCountOnStartButtonClick();
        onView(withId(R.id.startButton)).perform(click());
        Thread.sleep(1100);
        onView(withId(R.id.textView)).check(matches(withText("00:02")));
    }

    @Test
    public void testShouldIgnorePressingResetButtonAgain() throws InterruptedException {
        testShouldStopTimerAndResetCountOnResetButtonClick();
        Thread.sleep(1100);
        onView(withId(R.id.resetButton)).perform(click());
        Thread.sleep(1100);
        onView(withId(R.id.textView)).check(matches(withText("00:00")));
    }

}