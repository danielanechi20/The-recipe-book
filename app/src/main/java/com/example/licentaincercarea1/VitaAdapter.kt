package com.example.licentaincercarea1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.licentaincercarea1.data.category
import com.example.licentaincercarea1.data.vita
import com.example.licentaincercarea1.databinding.VitaItemBinding

class VitaAdapter(val retete : ArrayList<String>,
                  val ingrediente : ArrayList<String>,
                  val images: ArrayList<String>,
                  val pasi:ArrayList<String>) :
    RecyclerView.Adapter<VitaAdapter.VitaViewHolder>() {
   /* var listener: vitaClickListener? = null

    fun setCategoryClickListener(categoryListener: CategoryClickListener) {
        listener = vitaListener
    }*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            VitaViewHolder {
        val binding = VitaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VitaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VitaAdapter.VitaViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return retete.size
    }

    inner class VitaViewHolder(private val binding: VitaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val  retete = retete[adapterPosition]
            val images=retete[adapterPosition]
            binding.name.text = retete
            Glide.with(binding.root.context).load(images).fitCenter().into(binding.imagine)

        }

    }
    interface CategoryClickListener{
        fun onCategoryClick(category: String)
    }

}