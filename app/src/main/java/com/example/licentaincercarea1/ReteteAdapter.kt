package com.example.licentaincercarea1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.licentaincercarea1.data.reteta
import com.example.licentaincercarea1.databinding.ReteteItemBinding

class ReteteAdapter(val retete:List<reteta>) :
    RecyclerView.Adapter<ReteteAdapter.ReteteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ReteteViewHolder {
        val binding = ReteteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReteteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReteteAdapter.ReteteViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return retete.size
    }

    inner class ReteteViewHolder(private val binding: ReteteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val reteta=retete[adapterPosition]
            binding.name.text=reteta.Nume
            Glide.with(binding.root.context).load(reteta.Thumb).fitCenter().into(binding.imagine)
            binding.favourite.isChecked = reteta.Favourite
            binding.favourite.setOnClickListener {
                retete[adapterPosition].Favourite = !reteta.Favourite
            }
            setOnItemClickListener()
        }
        private fun setOnItemClickListener() {
            val reteta=retete[adapterPosition]
            binding.root.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("nume", reteta.Nume)
                    putString("ingrediente",reteta.In)
                    putString("imagine",reteta.Thumb)
                    putString("pasi", reteta.P)
                }
                it.findNavController().navigate(R.id.action_categoriesFragment_to_reteteFragment, bundle)
            }
        }
    }
}