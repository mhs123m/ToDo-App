package com.twq.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        var regFullName = findViewById<TextInputEditText>(R.id.editTextInputFullnameRegistration)
        var regEmail = findViewById<TextInputEditText>(R.id.editTextInputEmailRegistration)
        var regPhone = findViewById<TextInputEditText>(R.id.editTextInputPhoneRegistration)
        var regPass = findViewById<TextInputEditText>(R.id.editTextInputPasswordRegistration)
        var regConfPass =
            findViewById<TextInputEditText>(R.id.editTextInputConfirmPasswordRegistration)
        var regBtn = findViewById<Button>(R.id.buttonRegister)
        var tvSignIn = findViewById<TextView>(R.id.textViewSignIn)

        tvSignIn.setOnClickListener {
            var signinIntent = Intent(this,MainActivity::class.java)
            startActivity(signinIntent)
        }

        val db = Firebase.firestore

        regBtn.setOnClickListener {

            var auth = Firebase.auth



            if ((regPass.text.toString() == regConfPass.text.toString()) &&
                (Patterns.EMAIL_ADDRESS.matcher(regEmail.text.toString())
                    .matches()) && (Patterns.PHONE.matcher(regPhone.text.toString())
                    .matches()) && (regPass.text.toString().length > 8)
            ) {

                auth.createUserWithEmailAndPassword(
                    regEmail.text.toString(),
                    regPass.text.toString()
                )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {


                            val user = hashMapOf(
                                "FullName" to regFullName.text.toString(),
                                "email" to auth.currentUser?.email,
                                "phone" to regPhone.text.toString()
                            )

// Add a new document with a generated ID
                            db.collection("users")
                                .document(auth.currentUser?.uid.toString()).set(user)

                        }
                            else  Toast.makeText(this, "eror", Toast.LENGTH_SHORT)
                            .show()
                    }.addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            getString(R.string.failuar) + e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else Toast.makeText(this, "invalid email or password", Toast.
            LENGTH_SHORT).show()
        }
    }
}

