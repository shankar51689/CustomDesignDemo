package com.ms.compose.ux4gdeisgn2.ui.components

// DialogUtils.kt

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.ms.compose.ux4gdeisgn2.R

object DialogUtils {

    // Function to show a Material Dialog
   /* fun showMaterialDialog(
        activity: Activity,
        title: String,
        message: String,
        positiveButtonText: String = "Yes",
        negativeButtonText: String = "No",
        positiveAction: (() -> Unit)? = null,
        negativeAction: (() -> Unit)? = null
    ) {
        MaterialAlertDialogBuilder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { dialog, _ ->
                positiveAction?.invoke()
                dialog.dismiss()
            }
            .setNegativeButton(negativeButtonText) { dialog, _ ->
                negativeAction?.invoke()
                dialog.dismiss()
            }
            .show()
    }*/

    // Function to show a Custom Dialog
    fun showCustomDialog(
        activity: Activity,
        button: View, // Button that triggers the dialog
        title: String,
        message: String,
        icon: Drawable? = null,
        positiveButtonText: String = "Yes",
        negativeButtonText: String = "No",
        positiveAction: (() -> Unit)? = null,
        negativeAction: (() -> Unit)? = null
    ) {
        val dialogView = LayoutInflater.from(activity).inflate(R.layout.ux4g_alert_dialog, null)

        val dialog = AlertDialog.Builder(activity)
            .setView(dialogView)
            .setCancelable(false)
            .create()



        // Set dialog title and message
        dialogView.findViewById<TextView>(R.id.dialog_title).text = title
        dialogView.findViewById<TextView>(R.id.dialog_message).text = message

        val tvIcon       =  dialogView.findViewById<ImageView>(R.id.ivIcon)
        val btnNegative  =  dialogView.findViewById<Button>(R.id.btn_negative)
        val btnPositive  =  dialogView.findViewById<Button>(R.id.btn_positive)

        btnPositive.text = positiveButtonText
        btnNegative.text = negativeButtonText

        icon?.let {
            tvIcon.visibility = View.VISIBLE
            tvIcon.setImageDrawable(icon)
        } ?: {
            tvIcon.visibility = View.GONE
        }


        // Set positive button action
        dialogView.findViewById<Button>(R.id.btn_positive).setOnClickListener {
            positiveAction?.invoke()
            animateAndDismiss(dialog, activity, dialogView)
        }

        // Set negative button action
        dialogView.findViewById<Button>(R.id.btn_negative).setOnClickListener {
            negativeAction?.invoke()
            animateAndDismiss(dialog, activity, dialogView)
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // Transparent background
        // Apply Zoom-In animation when dialog appears
        dialog.setOnShowListener {
            val zoomIn = AnimationUtils.loadAnimation(activity, R.anim.dialog_fade_in)
            dialogView.startAnimation(zoomIn)
        }
        dialog.show()
    }


    // Function to animate and dismiss dialog
    private fun animateAndDismiss(dialog: AlertDialog, activity: Activity, dialogView: View) {
        val zoomOut = AnimationUtils.loadAnimation(activity, R.anim.dialog_fade_out)
        dialogView.startAnimation(zoomOut)

        zoomOut.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
            override fun onAnimationStart(animation: android.view.animation.Animation?) {}

            override fun onAnimationEnd(animation: android.view.animation.Animation?) {
                dialog.dismiss()
            }

            override fun onAnimationRepeat(animation: android.view.animation.Animation?) {}
        })
    }
}
