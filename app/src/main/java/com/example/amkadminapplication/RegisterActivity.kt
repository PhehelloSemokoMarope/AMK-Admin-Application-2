package com.example.amkadminapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        findViewById<TextView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val spinnerRole = findViewById<Spinner>(R.id.spinnerRole)
        val roles = arrayOf("Staff", "Designer")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, roles)
        spinnerRole.adapter = adapter

        val firstNameInput = findViewById<EditText>(R.id.inputFirstName)
        val surnameInput = findViewById<EditText>(R.id.inputSurname)
        val emailInput = findViewById<EditText>(R.id.inputRegEmail)
        val passwordInput = findViewById<EditText>(R.id.inputRegPassword)
        val confirmInput = findViewById<EditText>(R.id.inputConfirmPassword)

        findViewById<Button>(R.id.btnRegisterAccount).setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val confirm = confirmInput.text.toString().trim()
            val firstName = firstNameInput.text.toString().trim()
            val surname = surnameInput.text.toString().trim()
            val role = spinnerRole.selectedItem.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && password == confirm) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid
                            val userMap = hashMapOf(
                                "firstName" to firstName,
                                "surname" to surname,
                                "email" to email,
                                "role" to role
                            )

                            userId?.let {
                                db.collection("staff").document(it).set(userMap)
                            }

                            startActivity(Intent(this, MainActivity::class.java))
                            finishAffinity()
                        } else {
                            Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Check your inputs and try again", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btnGoBackToLogin).setOnClickListener {
            finish()
        }
    }
}