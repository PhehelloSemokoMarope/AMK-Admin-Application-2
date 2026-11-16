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

class ProcessPaymentActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_process_payment)

        db = FirebaseFirestore.getInstance()

        findViewById<TextView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        val inputCustomerName = findViewById<EditText>(R.id.inputPaymentCustomer)
        val inputOrderRef = findViewById<EditText>(R.id.inputPaymentOrderRef)
        val inputAmount = findViewById<EditText>(R.id.inputPaymentAmount)
        val methodSpinner = findViewById<Spinner>(R.id.spinnerPayMethod)

        val methods = arrayOf("Select method...", "Cash", "EFT", "Credit Card", "Mobile Money")
        methodSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, methods)

        findViewById<Button>(R.id.btnSubmitPayment).setOnClickListener {
            val customerName = inputCustomerName.text.toString().trim()
            val orderRef = inputOrderRef.text.toString().trim()
            val amount = inputAmount.text.toString().trim().toDoubleOrNull() ?: 0.0
            val method = methodSpinner.selectedItem.toString()

            if (amount <= 0.0 || method == "Select method...") {
                Toast.makeText(this, "Enter a valid amount and select a payment method", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val paymentRecord = hashMapOf(
                "customerName" to customerName,
                "orderRef" to orderRef,
                "amount" to amount,
                "method" to method,
                "status" to "Completed",
                "timestamp" to System.currentTimeMillis()
            )

            db.collection("payments")
                .add(paymentRecord)
                .addOnSuccessListener {
                    Toast.makeText(this, "Payment recorded", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Payment failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}