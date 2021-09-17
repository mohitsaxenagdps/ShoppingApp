package com.example.shoppingapp.Common

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.TextView
import com.example.shoppingapp.R

class CustomProgressDialog(private val context: Context) {

    private var dialog: Dialog = Dialog(context, R.style.CustomDialogTheme)

    @SuppressLint("InflateParams")
    fun showDialog(title: CharSequence?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.custom_progress_dialog, null)
        val cpTitle: TextView = view.findViewById(R.id.cp_title)
        if (title != null) {
            cpTitle.text = title
        }
        cpTitle.setTextColor(Color.WHITE)
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }

    fun dismissDialog() {
        dialog.dismiss()
    }

}