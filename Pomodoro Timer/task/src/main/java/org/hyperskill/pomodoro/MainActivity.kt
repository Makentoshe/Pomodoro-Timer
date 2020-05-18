package org.hyperskill.pomodoro

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.pomodoro.timer.TimerView
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    private val textView by lazy { findViewById<TimerView>(R.id.timerView) }
    private val startButton by lazy { findViewById<Button>(R.id.startButton) }
    private val resetButton by lazy { findViewById<Button>(R.id.resetButton) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        startButton.setOnClickListener {
            textView.color = Color.RED
            state = State.Work
            textView.text = "00:05"
            timerTask?.cancel()
            scheduleTask()
        }

        resetButton.setOnClickListener {
            timerTask?.cancel()
            timerTask = null
            textView.text = "00:05"
        }
    }

    private fun scheduleTask() {
        val now = ZonedDateTime.now()
        val target = getTargetTime(now)
        val between = ChronoUnit.SECONDS.between(now, target)
        if (between != 0L) {
            timerTask = timer.schedule(1000) {
                val currentTextViewTime = LocalTime.parse("00:${textView.text}", DateTimeFormatter.ofPattern("HH:mm:ss"))
                val targetTextViewTime = currentTextViewTime.minusSeconds(1)
                textView.text = targetTextViewTime.format(DateTimeFormatter.ofPattern("mm:ss"))
                state = State.Rest
                when (state) {
                    State.Work -> textView.color = Color.RED
                    State.Rest -> textView.color = Color.GREEN
                }
                if (textView.text == "00:01") {
                    textView.color = Color.YELLOW
                }
                scheduleTask()
            }
        }
    }

    private fun getTargetTime(now: ZonedDateTime): ZonedDateTime {
        val targetHack = LocalTime.parse("00:${textView.text}", DateTimeFormatter.ofPattern("HH:mm:ss"))
        return now.plusSeconds(targetHack.second.toLong()).plusMinutes(targetHack.minute.toLong())
    }

    companion object {
        private var state = State.Work
        private val timer = Timer()
        private var timerTask: TimerTask? = null
    }
}

enum class State { Work, Rest }