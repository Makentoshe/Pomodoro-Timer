package org.hyperskill.pomodoro

import android.content.res.ColorStateList
import android.graphics.Color
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
            settingsButton.isEnabled = false
        }

        resetButton.setOnClickListener {
            timer.stopCounting()
            textView.setText(R.string.initial_timer_state)
            textView.setTextColor(Color.DKGRAY)
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

            textView.setTextColor(if (upperLimitReached) Color.RED else Color.DKGRAY)

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
}
