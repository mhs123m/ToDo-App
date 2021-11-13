package com.twq.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.transition.Fade
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var imgLogo = findViewById<ImageView>(R.id.imageViewLogo)
        var tvDoMore = findViewById<TextView>(R.id.textViewDoMore)
        var tvOrganize = findViewById<TextView>(R.id.textViewOrganize)
        var tvMhteam = findViewById<TextView>(R.id.textViewPowered)



        var handler = Handler()

        handler.post {
            tvDoMore.visibility = View.VISIBLE
            val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
            tvDoMore.startAnimation(animation)

            tvOrganize.visibility = View.VISIBLE
            val organizeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
            tvOrganize.startAnimation(animation)

            tvMhteam.visibility = View.VISIBLE
            val mhTeamAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            tvMhteam.startAnimation(animation)
        }

        handler.postDelayed({


            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }, 3000)
    }
}