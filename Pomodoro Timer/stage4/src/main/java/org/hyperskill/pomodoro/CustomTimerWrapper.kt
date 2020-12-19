package org.hyperskill.pomodoro

import java.util.*

class CustomTimerWrapper {

    private var timer = Timer()

    private var isStarted: Boolean = false
    private var startSnapshot: Date? = null

    private var listener: ((Date, Date, Boolean) -> Unit)? = null
    fun setListener(listener: (Date, Date, Boolean) -> Unit) {
        this.listener = listener
    }

    // in seconds
    var upperLimit: Long = -1L

    fun startCounting(delayInMillis: Long) {
        if (isStarted) return
        isStarted = true
        startSnapshot = Calendar.getInstance().time
        performCounting(delayInMillis)
    }

    private fun performCounting(delayInMillis: Long) {
        val task = object : TimerTask() {
            override fun run() {
                // if time since start more that upper limit - stop counting
                val passedTime = (Calendar.getInstance().time.time - startSnapshot!!.time) / 1000
                val upperLimitReach = if (upperLimit == -1L) false else passedTime > upperLimit
                performCounting(delayInMillis)
                listener?.invoke(startSnapshot!!, Calendar.getInstance().time, upperLimitReach)
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