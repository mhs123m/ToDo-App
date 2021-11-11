package com.twq.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.twq.todoapp.Model.Setting

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        var textViewMemberInitials = findViewById<TextView>(R.id.textViewSettingsMemberInitials)
        var textViewMemberFullName = findViewById<TextView>(R.id.textViewSettingsFullName)





    }
}