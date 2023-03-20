package com.example.licentaincercarea1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.licentaincercarea1.databinding.VitaItemBinding

class ReteteAdapter(val retetele : ArrayList<String>,
                    val ingrediente : ArrayList<String>,
                    val images: ArrayList<String>,
                    val pasi:ArrayList<String>) :
    RecyclerView.Adapter<ReteteAdapter.VitaViewHolder>() {
    var listener: vitaClickListener? = null

    fun setCategoryClickListener(vitaListener: ReteteAdapter.vitaClickListener) {
        listener = vitaListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            VitaViewHolder {
        val binding = VitaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VitaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReteteAdapter.VitaViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return retetele.size
    }

    inner class VitaViewHolder(private val binding: VitaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val retete = retetele[adapterPosition]
            val images = retetele[adapterPosition]
            binding.name.text = retete
            Glide.with(binding.root.context).load(images).fitCenter().into(binding.imagine)
            setOnItemClickListener()
        }

        private fun setOnItemClickListener() {
            binding.root.setOnClickListener {
                listener?.onCategoryClick(retetele[adapterPosition])

            }
        }
    }
    interface vitaClickListener{
        fun onCategoryClick(retete: String)
    }

}