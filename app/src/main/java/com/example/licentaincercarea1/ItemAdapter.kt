package com.example.licentaincercarea1

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
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
                  private val selectedIngredients: ArrayList<ingredient>) :
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
                val type = types[adapterPosition]
                binding.tipul.text = type.tip
                for (ingredient in type.denumiri) {
                    val checkbox = CheckBox(binding.root.context)
                    checkbox.text = ingredient.nume
                    val editText = EditText(binding.root.context)
                    editText.inputType = InputType.TYPE_CLASS_NUMBER
                    editText.hint = "Cantitate"
                    val ingredientLayout = LinearLayout(binding.root.context)
                    ingredientLayout.orientation = LinearLayout.HORIZONTAL
                    ingredientLayout.addView(checkbox)
                    ingredientLayout.addView(editText)
                    checkbox.isChecked = ingredient.isChecked
                    binding.denumiriGroup.addView(ingredientLayout)
                    checkbox.setOnCheckedChangeListener { _, isChecked ->
                        ingredient.isChecked = isChecked
                        if (isChecked) {
                            val quantity = editText.text.toString().toIntOrNull()
                            if (quantity != null) {
                                selectedIngredients.add(ingredient)
                            }
                        } else {
                            selectedIngredients.remove(ingredient)
                        }
                    }
                    editText.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                        }

                        override fun afterTextChanged(s: Editable?) {
                            val quantity = s.toString().toIntOrNull()
                            if (checkbox.isChecked && quantity != null) {
                                selectedIngredients.remove(ingredient)
                                selectedIngredients.add(ingredient.copy(cantitate = quantity.toInt()))
                            }
                        }
                    })
                }
            }
        }
    }
