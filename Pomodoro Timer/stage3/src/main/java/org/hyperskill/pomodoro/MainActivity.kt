package org.hyperskill.pomodoro

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val timer = CustomTimerWrapper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
            timer.startCounting(1000)
            progressBar.visibility = View.VISIBLE
        }

        resetButton.setOnClickListener {
            timer.stopCounting()
            textView.setText(R.string.initial_timer_state)
            progressBar.visibility = View.GONE
        }

        timer.setListener { start, current ->
            val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())
            val string = formatter.format(Date(current.time - start.time))
            textView.text = string
            runOnUiThread {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    progressBar.indeterminateTintList = ColorStateList.valueOf(Random.nextInt())
                    println(progressBar.indeterminateTintList?.defaultColor)
                } else {
                    progressBar.progressDrawable.setColorFilter(Random.nextInt(), PorterDuff.Mode.SRC_IN)
                }
            }
        }
    }
}

class CustomTimerWrapper {

    private var timer = Timer()

    private var isStarted: Boolean = false
    private var startSnapshot: Date? = null

    private var listener: ((Date, Date) -> Unit)? = null

    fun setListener(listener: (Date, Date) -> Unit) {
        this.listener = listener
    }

    fun startCounting(delayInMillis: Long) {
        if (isStarted) return
        isStarted = true
        startSnapshot = Calendar.getInstance().time
        performCounting(delayInMillis)
    }

    private fun performCounting(delayInMillis: Long) {
        val task = object : TimerTask() {
            override fun run() {
                performCounting(delayInMillis)
                listener?.invoke(startSnapshot!!, Calendar.getInstance().time)
            }
        }
        timer.schedule(task, delayInMillis)
    }

    fun stopCounting() {
        timer.cancel()
        timer = Timer()
        isStarted = false
    }
}