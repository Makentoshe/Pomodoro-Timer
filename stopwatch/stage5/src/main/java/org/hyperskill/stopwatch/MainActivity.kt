package org.hyperskill.stopwatch

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    companion object {
        private const val CHANNEL_ID = "org.hyperskill"
        private const val NOTIFICATION_ID = 393939
    }

    private val timer = CustomTimerWrapper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
            timer.startCounting(1000)
            progressBar.visibility = View.VISIBLE
            settingsButton.isEnabled = false
        }

        resetButton.setOnClickListener {
            timer.stopCounting()
            textView.setText(R.string.initial_timer_state)
            progressBar.visibility = View.GONE
            settingsButton.isEnabled = true
        }

        settingsButton.setOnClickListener {
            SettingsDialogFragment().show(supportFragmentManager, SettingsDialogFragment::class.java.simpleName)
        }

        timer.setListener { start, current, upperLimitReached ->
            val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())
            val string = formatter.format(Date(current.time - start.time))
            textView.text = string

            if (upperLimitReached) {
                textView.setTextColor(Color.RED)
                createTimerExceedNotify()
            } else {
                textView.setTextColor(Color.DKGRAY)
            }

            runOnUiThread {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    progressBar.indeterminateTintList = ColorStateList.valueOf(Random.nextInt())
                } else {
                    progressBar.progressDrawable.setColorFilter(Random.nextInt(), PorterDuff.Mode.SRC_IN)
                }
            }
        }
    }

    // just a hack to avoid default activity result api
    fun setUpperLimit(string: CharSequence) {
        timer.upperLimit = string.toString().toLongOrNull() ?: -1
    }

    private fun createTimerExceedNotify() {
        val notificationManager = NotificationManagerCompat.from(this)
        var channel = notificationManager.getNotificationChannel(CHANNEL_ID)
        if (channel == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = createNotificationChannel()
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Notification")
            .setContentText("Time exceeded")
            .build()
        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): NotificationChannel {
        val applicationTitle = getString(R.string.app_name)
        val importance = NotificationManager.IMPORTANCE_HIGH

        return NotificationChannel(CHANNEL_ID, "$applicationTitle channel", importance).apply {
            description = "$applicationTitle channel description"
            lightColor = Color.RED
            enableLights(true)
            enableVibration(false)
        }
    }
}
