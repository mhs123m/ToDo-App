package com.twq.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        var textViewMemberInitials = findViewById<TextView>(R.id.textViewSettingsMemberInitials)
        var textViewMemberFullName = findViewById<TextView>(R.id.textViewSettingsFullName)
        var mToolBarSetting = findViewById<Toolbar>(R.id.mToolBarSetting)


        mToolBarSetting.title = "Settings"

        mToolBarSetting.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)

        setSupportActionBar(mToolBarSetting)
        mToolBarSetting.setNavigationOnClickListener {
            finish()
        }





    }
}