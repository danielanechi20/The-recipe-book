package com.example.licentaincercarea1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.example.licentaincercarea1.data.category
import com.example.licentaincercarea1.data.ingredient
import com.example.licentaincercarea1.data.reteta
import com.example.licentaincercarea1.data.types
import com.example.licentaincercarea1.databinding.IngrItemBinding
import com.example.licentaincercarea1.databinding.ReteteItemBinding

class ItemAdapter(var types:List<types>,
                  private val selectedIngredients: MutableList<ingredient>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ItemViewHolder {
        val binding = IngrItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return types.size
    }


    inner class ItemViewHolder(private val binding: IngrItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val type=types[adapterPosition]
            binding.tipul.text = type.tip
            for (ingredient in type.denumiri) {
                val checkbox = CheckBox(binding.root.context)
                checkbox.text = ingredient.nume
                binding.denumiriGroup.addView(checkbox)
                checkbox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedIngredients.add(ingredient)
                    } else {
                        selectedIngredients.remove(ingredient)
                    }
                }
            }
        }


    }
}