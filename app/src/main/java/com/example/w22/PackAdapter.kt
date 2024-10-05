package com.example.w22

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class PackAdapter(private val packList: List<Pack>) :
    RecyclerView.Adapter<PackAdapter.PackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pack, parent, false)
        return PackViewHolder(view)
    }

    override fun onBindViewHolder(holder: PackViewHolder, position: Int) {
        val pack = packList[position]
        holder.name.text = pack.name
        holder.description.text = pack.description
        holder.pricePerHour.text = "Price per hour: ${pack.pricePerHour}"
        holder.additionalHourPrice.text = "Additional hour price: ${pack.additionalHourPrice}"
        holder.features.text = "Features: ${pack.features.joinToString(", ")}"
    }

    override fun getItemCount(): Int {
        return packList.size
    }

    class PackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val description: TextView = itemView.findViewById(R.id.description)
        val pricePerHour: TextView = itemView.findViewById(R.id.pricePerHour)
        val additionalHourPrice: TextView = itemView.findViewById(R.id.additionalHourPrice)
        val features: TextView = itemView.findViewById(R.id.features)
    }
}
