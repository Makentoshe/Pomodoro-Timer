package org.hyperskill.pomodoro

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class CustomDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(R.layout.activity_main_dialog)
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        builder.setPositiveButton("OK") { dialog, _ ->
            onPositiveClick(dialog as AlertDialog)
            dismiss()
        }
        return builder.create()
    }

    private fun Editable?.nullIfBlank() = if (this?.isBlank() == false) toString() else null


    private fun onPositiveClick(dialog: AlertDialog) {
        val restTime = dialog.findViewById<EditText>(R.id.restTime)?.text?.nullIfBlank()?.toIntOrNull() ?: 0
        val workTime = dialog.findViewById<EditText>(R.id.workTime)?.text.nullIfBlank()?.toIntOrNull() ?: 0
        val intent = Intent().putExtra("rest", restTime).putExtra("work", workTime)
        (requireActivity() as MainActivity).onActivityResult(1, 1, intent)
    }

    class Factory {

        fun show(parentFragmentManager: FragmentManager): CustomDialogFragment {
            val fragment = CustomDialogFragment()
            fragment.show(parentFragmentManager, CustomDialogFragment::class.java.simpleName)
            return fragment
        }
    }
}