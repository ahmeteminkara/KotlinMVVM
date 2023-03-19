package com.aek.kotlinmvvm.core.base

import android.app.AlertDialog
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    private var alertDialog: AlertDialog? = null

    protected fun showAlertDialog(message: String, onClose: (() -> Unit)? = null) {
        if (alertDialog == null) {
            alertDialog = AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("Close") { dialog, _ ->
                    onClose?.invoke()
                    dialog.dismiss()
                }
                .create()
        }

        alertDialog?.setMessage(message)
        alertDialog?.show()
    }
}
