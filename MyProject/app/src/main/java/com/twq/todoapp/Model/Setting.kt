package com.twq.todoapp.Model

import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import java.io.Serializable

data class Setting(
    var icon: ImageView,
    var spinner: Spinner
) : Serializable {
}