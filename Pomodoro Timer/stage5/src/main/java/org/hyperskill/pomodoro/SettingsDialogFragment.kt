package org.hyperskill.pomodoro

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class SettingsDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.upper_limit_title)
        builder.setView(R.layout.dialog_settings)
        builder.setNegativeButton(R.string.cancel) { dialog, _ ->
            dialog.cancel()
        }
        builder.setPositiveButton(R.string.positive) { _, _ ->
            val string = requireDialog().findViewById<TextView>(R.id.upperLimitEditText).text
            (requireActivity() as MainActivity).setUpperLimit(string)
        }
        return builder.create()
    }
}