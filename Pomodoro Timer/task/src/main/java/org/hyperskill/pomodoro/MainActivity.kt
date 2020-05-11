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
            val now = ZonedDateTime.now()
            val targetHack = LocalTime.parse("00:${textView.text}", DateTimeFormatter.ofPattern("HH:mm:ss"))
            val targetTime = now.plusSeconds(targetHack.second.toLong()).plusMinutes(targetHack.minute.toLong())
            if (ChronoUnit.SECONDS.between(now, targetTime) != 0L) {
                timerTask = timer.schedule(1000) {
                }
            }
        }

        resetButton.setOnClickListener {
            timerTask?.cancel()
            timerTask = null
            textView.text = "00:03"
        }
    }

    private fun scheduleTask() {
        val now = ZonedDateTime.now()
        val targetHack = LocalTime.parse("00:${textView.text}", DateTimeFormatter.ofPattern("HH:mm:ss"))
        val targetTime = now.plusSeconds(targetHack.second.toLong()).plusMinutes(targetHack.minute.toLong())
        if (ChronoUnit.SECONDS.between(now, targetTime) != 0L) {
            timerTask = timer.schedule(1000) { scheduleTask() }
        }
    }

    companion object {
        private val timer = Timer()
        private var timerTask: TimerTask? = null
    }
}