package com.example.amkadminapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class CustomerManagementActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var rvCustomerList: RecyclerView
    private lateinit var customerAdapter: CustomerAdapter
    private val customerList = mutableListOf<Customer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_management)

        db = FirebaseFirestore.getInstance()

        rvCustomerList = findViewById(R.id.rvCustomerList)
        rvCustomerList.layoutManager = LinearLayoutManager(this)

        customerAdapter = CustomerAdapter(customerList)
        rvCustomerList.adapter = customerAdapter

        findViewById<TextView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnAddCustomer).setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_add_customer, null)
            val dialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .create()

            dialogView.findViewById<Button>(R.id.btnSaveNewCustomer).setOnClickListener {
                val name = dialogView.findViewById<EditText>(R.id.etCustName).text.toString().trim()
                val phone = dialogView.findViewById<EditText>(R.id.etCustPhone).text.toString().trim()
                val email = dialogView.findViewById<EditText>(R.id.etCustEmail).text.toString().trim()

                if (name.isNotEmpty() && phone.isNotEmpty()) {
                    val newCustomer = hashMapOf(
                        "name" to name,
                        "phone" to phone,
                        "email" to email
                    )
                    db.collection("customers").add(newCustomer).addOnSuccessListener {
                        Toast.makeText(this, "Customer Saved!", Toast.LENGTH_SHORT).show()
                        fetchCustomerData()
                        dialog.dismiss()
                    }
                }
            }
            dialog.show()
        }

        fetchCustomerData()
    }

    private fun fetchCustomerData() {
        db.collection("customers").get()
            .addOnSuccessListener { documents ->
                customerList.clear()
                for (document in documents) {
                    val customer = document.toObject(Customer::class.java)
                    customerList.add(customer)
                }
                customerAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load customers", Toast.LENGTH_SHORT).show()
            }
    }
}