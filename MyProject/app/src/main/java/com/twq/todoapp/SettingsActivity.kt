package com.twq.todoapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.twq.todoapp.Fragments.todoList
import com.twq.todoapp.Model.ToDo

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        var textViewMemberInitials = findViewById<TextView>(R.id.textViewSettingsMemberInitials)
        var textViewMemberFullName = findViewById<TextView>(R.id.textViewSettingsFullName)
        var mToolBarSetting = findViewById<Toolbar>(R.id.mToolBarSetting)
        var mCardViewSignout =findViewById<CardView>(R.id.cardViewHoldingSignOut)
        var mCardViewShareApp = findViewById<CardView>(R.id.cardViewShareApp)
        var mCardViewContactDeveloper = findViewById<CardView>(R.id.cardViewContactDeveloper)

        mToolBarSetting.title = "Settings"

        mToolBarSetting.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)

        setSupportActionBar(mToolBarSetting)
        mToolBarSetting.setNavigationOnClickListener {
            finish()
        }

        var db = Firebase.firestore
        var auth = Firebase.auth
        db.collection("users")
            .document(auth.currentUser?.uid.toString())
            .addSnapshotListener { user, error ->
                if (user != null) {
                   textViewMemberInitials.text = user.getString("FullName")?.substring(0,2)?.toUpperCase()
                   textViewMemberFullName.text = user.getString("FullName")
                }
            }

        mCardViewShareApp.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Hey check out my app at (AppStore app id)")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        mCardViewContactDeveloper.setOnClickListener {

            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("tel: 0549436004")
            startActivity(callIntent)
        }

        mCardViewSignout.setOnClickListener {
            println("sign out is pressed")
            Firebase.auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        or Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
            startActivity(intent)
            finish()
        }

    }
    private fun Logout() {
        Firebase.auth.signOut()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_CLEAR_TASK
        )
        startActivity(intent)
        finish()
    }
}