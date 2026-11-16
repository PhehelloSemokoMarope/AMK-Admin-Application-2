package com.example.amkadminapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class ManageProductsActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_products)

        db = FirebaseFirestore.getInstance()

        findViewById<TextView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        val inputProductName = findViewById<EditText>(R.id.inputProductName)
        val inputProductPrice = findViewById<EditText>(R.id.inputProductPrice)
        val inputProductCategory = findViewById<EditText>(R.id.inputProductCategory)
        val inputProductDescription = findViewById<EditText>(R.id.inputProductDescription)

        findViewById<Button>(R.id.btnUploadProduct).setOnClickListener {
            val name = inputProductName.text.toString().trim()
            val price = inputProductPrice.text.toString().trim().toDoubleOrNull() ?: 0.0
            val category = inputProductCategory.text.toString().trim()
            val description = inputProductDescription.text.toString().trim()

            if (name.isEmpty() || price <= 0.0) {
                Toast.makeText(this, "Enter a valid product name and price", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val productData = hashMapOf(
                "name" to name,
                "price" to price,
                "category" to category,
                "description" to description,
                "createdAt" to System.currentTimeMillis()
            )

            db.collection("products")
                .add(productData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Product uploaded", Toast.LENGTH_SHORT).show()
                    inputProductName.text.clear()
                    inputProductPrice.text.clear()
                    inputProductCategory.text.clear()
                    inputProductDescription.text.clear()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Upload failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}