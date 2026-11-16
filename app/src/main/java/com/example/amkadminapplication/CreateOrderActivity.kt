package com.example.amkadminapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class CreateOrderActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_order)

        db = FirebaseFirestore.getInstance()

        findViewById<TextView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        val modeSpinner = findViewById<Spinner>(R.id.spinnerOrderMode)
        val modes = arrayOf("Create New Order from Scratch", "Review & Finalize Online Request")
        modeSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, modes)

        val customerSpinner = findViewById<Spinner>(R.id.spinnerCustomer)
        val customerNames = mutableListOf("Select a customer...")
        val customerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, customerNames)
        customerSpinner.adapter = customerAdapter

        db.collection("customers").get().addOnSuccessListener { documents ->
            for (document in documents) {
                document.getString("name")?.let { customerNames.add(it) }
            }
            customerAdapter.notifyDataSetChanged()
        }

        val fabricSpinner = findViewById<Spinner>(R.id.spinnerFabric)
        val fabrics = arrayOf("Select fabric...", "Shweshwe", "Silk Satin", "Cotton")
        fabricSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, fabrics)

        val statusSpinner = findViewById<Spinner>(R.id.spinnerOrderStatus)
        val statuses = arrayOf("Processing", "In-progress", "Completed")
        statusSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, statuses)

        findViewById<Button>(R.id.btnSaveOrder).setOnClickListener {
            val garment = findViewById<EditText>(R.id.inputGarment).text.toString().trim()
            val amount = findViewById<EditText>(R.id.inputOrderAmount).text.toString().toDoubleOrNull() ?: 0.0
            val status = statusSpinner.selectedItem.toString()

            if (garment.isNotEmpty() && amount > 0) {
                val newOrder = hashMapOf(
                    "garment" to garment,
                    "amount" to amount,
                    "status" to status,
                    "timestamp" to System.currentTimeMillis()
                )

                db.collection("orders").add(newOrder)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Order saved successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error saving order", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Please fill out the required fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}