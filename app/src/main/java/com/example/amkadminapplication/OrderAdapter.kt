package com.example.amkadminapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter(private val orderList: List<Order>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvGarment: TextView = view.findViewById(R.id.tvOrderGarment)
        val tvAmount: TextView = view.findViewById(R.id.tvOrderAmount)
        val tvStatus: TextView = view.findViewById(R.id.tvOrderStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.tvGarment.text = order.garment
        holder.tvAmount.text = "R ${order.amount}"
        holder.tvStatus.text = "Status: ${order.status}"
    }

    override fun getItemCount(): Int = orderList.size
}