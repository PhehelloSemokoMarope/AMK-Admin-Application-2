package com.example.amkadminapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StaffAdapter(private val staffList: List<Staff>) :
    RecyclerView.Adapter<StaffAdapter.StaffViewHolder>() {

    class StaffViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvStaffName)
        val tvRole: TextView = view.findViewById(R.id.tvStaffRole)
        val tvEmail: TextView = view.findViewById(R.id.tvStaffEmail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_staff, parent, false)
        return StaffViewHolder(view)
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val staff = staffList[position]
        holder.tvName.text = "${staff.firstName} ${staff.surname}"
        holder.tvRole.text = staff.role
        holder.tvEmail.text = staff.email
    }

    override fun getItemCount(): Int = staffList.size
}