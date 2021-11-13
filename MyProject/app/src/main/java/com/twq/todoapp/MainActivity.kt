package com.twq.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private var auth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        var loginEmail = findViewById<TextInputEditText>(R.id.editTextnputEmailLogin)
        var loginPassword = findViewById<TextInputEditText>(R.id.editTextInputPasswordLogin)
        var loginBtn = findViewById<Button>(R.id.buttonLogin)
        var noAccountTextView = findViewById<TextView>(R.id.textViewNoAccountSignUp)

        noAccountTextView.setOnClickListener {
            var i = Intent(this, RegistrationActivity::class.java)

            startActivity(i)
        }

        loginBtn.setOnClickListener {
            var auth = Firebase.auth

            if (loginEmail.text.toString().isNotEmpty()
                && loginPassword.text.toString().isNotEmpty()
            ) {


                auth.signInWithEmailAndPassword(
                    loginEmail.text.toString(), loginPassword.text.toString()
                )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            var user = auth.currentUser
                            var i = Intent(this,HomeActivity::class.java)
                            startActivity(i)
                            finish()
                        } else {

                            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT)
                                .show()
                        }

                    }
            }
        }
    }

}
