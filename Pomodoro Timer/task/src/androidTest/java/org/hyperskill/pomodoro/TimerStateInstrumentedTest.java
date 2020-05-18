package org.hyperskill.pomodoro;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


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
    public void timerViewExist() {
    }

    @Test
    public void startTimer() throws InterruptedException {
    }

    @Test
    public void resetTimer() throws InterruptedException {
    }

    @Test
    public void interruptTimer() throws InterruptedException {
    }

}