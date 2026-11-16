package com.example.amkadminapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class RecordMeasurementsActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_measurements)

        db = FirebaseFirestore.getInstance()

        findViewById<TextView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        val inputCustomerName = findViewById<EditText>(R.id.inputMeasureCustomer)
        val inputChest = findViewById<EditText>(R.id.inputChest)
        val inputWaist = findViewById<EditText>(R.id.inputWaist)
        val inputHips = findViewById<EditText>(R.id.inputHips)
        val inputLength = findViewById<EditText>(R.id.inputLength)
        val inputNotes = findViewById<EditText>(R.id.inputMeasurementNotes)

        findViewById<Button>(R.id.btnSaveMeasurements).setOnClickListener {
            val customerName = inputCustomerName.text.toString().trim()
            val chest = inputChest.text.toString().trim()
            val waist = inputWaist.text.toString().trim()
            val hips = inputHips.text.toString().trim()
            val length = inputLength.text.toString().trim()
            val notes = inputNotes.text.toString().trim()

            if (customerName.isEmpty()) {
                Toast.makeText(this, "Enter customer name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val measurementData = hashMapOf(
                "customerName" to customerName,
                "chest" to chest,
                "waist" to waist,
                "hips" to hips,
                "length" to length,
                "notes" to notes,
                "updatedAt" to System.currentTimeMillis()
            )

            db.collection("measurements")
                .add(measurementData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Measurements saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to save measurements: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}