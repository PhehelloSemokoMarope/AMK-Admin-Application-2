package com.example.amkadminapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InventoryAdapter(private val inventoryList: List<InventoryItem>) :
    RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {

    class InventoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvInvName)
        val tvQty: TextView = view.findViewById(R.id.tvInvQty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inventory, parent, false)
        return InventoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        val item = inventoryList[position]
        holder.tvName.text = "${item.itemName} (${item.category})"
        holder.tvQty.text = "Stock: ${item.quantity} ${item.unit}"
    }

    override fun getItemCount(): Int = inventoryList.size
}