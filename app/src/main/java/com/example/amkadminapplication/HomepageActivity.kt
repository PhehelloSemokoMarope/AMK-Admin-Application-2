package com.example.amkadminapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomepageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

        findViewById<Button>(R.id.btnNavProducts).setOnClickListener {
            startActivity(Intent(this, ManageProductsActivity::class.java))
        }

        findViewById<Button>(R.id.btnNavStaff).setOnClickListener {
            startActivity(Intent(this, StaffManagementActivity::class.java))
        }

        findViewById<Button>(R.id.btnNavCustomers).setOnClickListener {
            startActivity(Intent(this, CustomerManagementActivity::class.java))
        }

        findViewById<Button>(R.id.btnNavOrders).setOnClickListener {
            startActivity(Intent(this, CreateOrderActivity::class.java))
        }

        findViewById<Button>(R.id.btnNavChats).setOnClickListener {
            startActivity(Intent(this, AdminMessagesActivity::class.java))
        }

        findViewById<Button>(R.id.btnNavPayments).setOnClickListener {
            startActivity(Intent(this, ProcessPaymentActivity::class.java))
        }

        findViewById<Button>(R.id.btnNavInventory).setOnClickListener {
            startActivity(Intent(this, AddItemsActivity::class.java))
        }

        findViewById<Button>(R.id.btnNavReports).setOnClickListener {
            val intent = Intent(this, ReportActivity::class.java)
            intent.putExtra("REPORT_TYPE", "Sales Report")
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnNavMeasurements).setOnClickListener {
            startActivity(Intent(this, RecordMeasurementsActivity::class.java))
        }

        loadDashboardMetrics()
    }

    private fun loadDashboardMetrics() {
        val tvTotalOrders = findViewById<TextView>(R.id.tvTotalOrders)

        db.collection("orders").addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                tvTotalOrders.text = snapshot.size().toString()
            }
        }
    }
}