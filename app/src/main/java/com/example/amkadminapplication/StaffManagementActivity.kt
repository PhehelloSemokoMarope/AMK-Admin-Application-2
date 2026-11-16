package com.example.amkadminapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class StaffManagementActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var rvStaffList: RecyclerView
    private lateinit var staffAdapter: StaffAdapter
    private val staffList = mutableListOf<Staff>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_management)

        db = FirebaseFirestore.getInstance()

        rvStaffList = findViewById(R.id.rvStaffList)
        rvStaffList.layoutManager = LinearLayoutManager(this)

        staffAdapter = StaffAdapter(staffList)
        rvStaffList.adapter = staffAdapter

        val btnBack = findViewById<TextView>(R.id.btnBack)
        if (btnBack != null) {
            btnBack.setOnClickListener { finish() }
        }

        findViewById<Button>(R.id.btnAddUser).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        fetchStaffData()
    }

    private fun fetchStaffData() {
        db.collection("staff").get()
            .addOnSuccessListener { documents ->
                staffList.clear()
                for (document in documents) {
                    val staff = document.toObject(Staff::class.java)
                    staffList.add(staff)
                }
                staffAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load staff", Toast.LENGTH_SHORT).show()
            }
    }
}