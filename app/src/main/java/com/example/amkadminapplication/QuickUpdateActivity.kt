package com.example.amkadminapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class QuickUpdateActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_update)

        db = FirebaseFirestore.getInstance()

        findViewById<TextView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        val updateType = intent.getStringExtra("UPDATE_TYPE") ?: "Quick Update"
        findViewById<TextView>(R.id.tvUpdateTitle).text = updateType

        val inputReferenceId = findViewById<EditText>(R.id.inputUpdateTargetId)
        val inputStatusOrValue = findViewById<EditText>(R.id.inputUpdateValue)

        findViewById<Button>(R.id.btnSubmitUpdate).setOnClickListener {
            val targetId = inputReferenceId.text.toString().trim()
            val newValue = inputStatusOrValue.text.toString().trim()

            if (newValue.isEmpty()) {
                Toast.makeText(this, "Enter an update value", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val logEntry = hashMapOf(
                "updateType" to updateType,
                "targetId" to targetId,
                "value" to newValue,
                "timestamp" to System.currentTimeMillis()
            )

            db.collection("stock_updates")
                .add(logEntry)
                .addOnSuccessListener {
                    Toast.makeText(this, "Update saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Update failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}