package dev.mjpark.krtv

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmationFragment(
    private val listener: ConfirmationListener,
    private val message: String,
    private val update: Boolean
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(message)
            if (update) {
                builder.setMessage("??????")
                    .setPositiveButton(
                        "??"
                    ) { _, _ ->
                        listener.onConfirm()
                    }
                    .setNegativeButton(
                        "??"
                    ) { _, _ ->
                        listener.onCancel()
                    }
            } else {
                builder.setMessage("")
                    .setNegativeButton(
                        "??"
                    ) { _, _ ->
                    }
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    interface ConfirmationListener {
        fun onConfirm()
        fun onCancel()
    }
}

