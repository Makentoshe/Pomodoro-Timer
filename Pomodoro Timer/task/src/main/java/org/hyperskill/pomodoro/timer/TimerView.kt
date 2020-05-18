package org.hyperskill.pomodoro.timer

import android.content.Context
import android.util.AttributeSet

class TimerView(
        context: Context, attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatTextView(context, attrs) {

    var color: Int = 0
}