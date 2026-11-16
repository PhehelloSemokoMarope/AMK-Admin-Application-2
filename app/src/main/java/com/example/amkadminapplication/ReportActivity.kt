package com.example.amkadminapplication

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ReportActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var rvReportList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        db = FirebaseFirestore.getInstance()
        rvReportList = findViewById(R.id.rvReportList)
        rvReportList.layoutManager = LinearLayoutManager(this)

        findViewById<TextView>(R.id.btnBack).setOnClickListener { finish() }

        val reportType = intent.getStringExtra("REPORT_TYPE") ?: "System Report"
        findViewById<TextView>(R.id.tvReportTitle).text = reportType

        when (reportType) {
            "Sales Report", "Customer Orders" -> loadOrdersReport()
            "Inventory" -> loadInventoryReport()
            else -> Toast.makeText(this, "Unknown Report Type", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadOrdersReport() {
        val orderList = mutableListOf<Order>()
        val adapter = OrderAdapter(orderList)
        rvReportList.adapter = adapter

        db.collection("orders").get().addOnSuccessListener { documents ->
            orderList.clear()
            for (document in documents) {
                val order = document.toObject(Order::class.java)
                orderList.add(order)
            }
            adapter.notifyDataSetChanged()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to load orders", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadInventoryReport() {
        val inventoryList = mutableListOf<InventoryItem>()
        val adapter = InventoryAdapter(inventoryList)
        rvReportList.adapter = adapter

        db.collection("inventory").get().addOnSuccessListener { documents ->
            inventoryList.clear()
            for (document in documents) {
                val item = document.toObject(InventoryItem::class.java)
                inventoryList.add(item)
            }
            adapter.notifyDataSetChanged()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to load inventory", Toast.LENGTH_SHORT).show()
        }
    }
}