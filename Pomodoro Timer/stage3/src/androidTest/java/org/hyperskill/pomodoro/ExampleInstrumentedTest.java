package org.hyperskill.pomodoro;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ProgressBar;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
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
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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

    @Test
    public void testShouldCheckProgressBarInvisibilityOnInit() {
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testShouldCheckProgressBarVisibilityOnStart() throws InterruptedException {
        onView(withId(R.id.startButton)).perform(click());
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
    }

    @Test
    public void testShouldCheckProgressBarInvisibilityOnReset() throws InterruptedException {
        onView(withId(R.id.startButton)).perform(click());
        Thread.sleep(100L);
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
        onView(withId(R.id.resetButton)).perform(click());
        Thread.sleep(100L);
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testShouldCheckProgressBarColorEachSecond10() throws InterruptedException {
        onView(withId(R.id.startButton)).perform(click());
        Thread.sleep(100L);
        final Integer[] lastColor = {null};
        for (int i = 0; i < 10; i++) {
            onView(withId(R.id.progressBar)).check(new ViewAssertion() {
                @Override
                public void check(View view, NoMatchingViewException noViewFoundException) {
                    ProgressBar progressBar = (ProgressBar) view;
                    ColorStateList list = progressBar.getIndeterminateTintList();
                    Integer color;
                    if (list == null) {
                        color = null;
                    } else {
                        color = list.getDefaultColor();
                    }
                    assertNotEquals(color, lastColor[0]);
                    lastColor[0] = color;
                }
            });
            Thread.sleep(1000L);
        }
    }
}
