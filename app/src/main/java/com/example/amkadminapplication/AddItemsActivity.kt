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

class AddItemsActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_items)

        db = FirebaseFirestore.getInstance()

        val categorySpinner = findViewById<Spinner>(R.id.spinnerCategory)
        val categories = arrayOf("Fabric", "Thread", "Accessories")
        categorySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)

        val unitSpinner = findViewById<Spinner>(R.id.spinnerUnit)
        val units = arrayOf("Meters", "Spools", "Rolls", "Units")
        unitSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, units)

        findViewById<TextView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnSaveItem).setOnClickListener {
            val itemName = findViewById<EditText>(R.id.inputItemName).text.toString().trim()
            val category = categorySpinner.selectedItem.toString()
            val supplier = findViewById<EditText>(R.id.inputSupplier).text.toString().trim()
            val color = findViewById<EditText>(R.id.inputColor).text.toString().trim()
            val quantity = findViewById<EditText>(R.id.inputQuantity).text.toString().toIntOrNull() ?: 0
            val unit = unitSpinner.selectedItem.toString()
            val reorderLevel = findViewById<EditText>(R.id.inputReorder).text.toString().toIntOrNull() ?: 0
            val cost = findViewById<EditText>(R.id.inputCost).text.toString().toDoubleOrNull() ?: 0.0

            if (itemName.isNotEmpty() && supplier.isNotEmpty()) {
                val inventoryItem = hashMapOf(
                    "itemName" to itemName,
                    "category" to category,
                    "supplier" to supplier,
                    "color" to color,
                    "quantity" to quantity,
                    "unit" to unit,
                    "reorderLevel" to reorderLevel,
                    "cost" to cost
                )

                db.collection("inventory").add(inventoryItem)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Item successfully added!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error saving item: ${e.message}", Toast.LENGTH_LONG).show()
                    }
            } else {
                Toast.makeText(this, "Please fill out required fields.", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btnClearForm).setOnClickListener {
            findViewById<EditText>(R.id.inputItemName).text.clear()
            findViewById<EditText>(R.id.inputSupplier).text.clear()
            findViewById<EditText>(R.id.inputColor).text.clear()
            findViewById<EditText>(R.id.inputQuantity).text.clear()
            findViewById<EditText>(R.id.inputReorder).text.clear()
            findViewById<EditText>(R.id.inputCost).text.clear()
        }
    }
}