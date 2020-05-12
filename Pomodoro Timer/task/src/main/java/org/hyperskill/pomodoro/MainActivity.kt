package org.hyperskill.pomodoro

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    private val textView by lazy { findViewById<TextView>(R.id.timerView) }
    private val startButton by lazy { findViewById<Button>(R.id.startButton) }
    private val resetButton by lazy { findViewById<Button>(R.id.resetButton) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        startButton.setOnClickListener {
            textView.text = "00:03"
            scheduleTask()
        }

        resetButton.setOnClickListener {
            timerTask?.cancel()
            timerTask = null
            textView.text = "00:03"
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
                scheduleTask()
            }
        }
    }

    private fun getTargetTime(now: ZonedDateTime): ZonedDateTime {
        val targetHack = LocalTime.parse("00:${textView.text}", DateTimeFormatter.ofPattern("HH:mm:ss"))
        return now.plusSeconds(targetHack.second.toLong()).plusMinutes(targetHack.minute.toLong())
    }

    companion object {
        private val timer = Timer()
        private var timerTask: TimerTask? = null
    }
}