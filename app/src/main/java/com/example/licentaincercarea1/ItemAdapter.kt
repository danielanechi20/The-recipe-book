package com.example.licentaincercarea1


import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.licentaincercarea1.data.ingredient
import com.example.licentaincercarea1.data.types
import com.example.licentaincercarea1.databinding.IngrItemBinding

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

                    val textView = TextView(binding.root.context)
                    textView.text = ingredient.masura

                    checkbox.buttonDrawable = null

                    val ingredientLayout = FrameLayout(binding.root.context)

                    ingredientLayout.background = ContextCompat.getDrawable(binding.root.context, R.drawable.ingredient_background)
                    val layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.setMargins(16, 16, 16, 16)
                    ingredientLayout.layoutParams = layoutParams

                    val linearLayout = LinearLayout(binding.root.context)
                    linearLayout.orientation = LinearLayout.HORIZONTAL

                    val linearLayoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    )
                    linearLayout.layoutParams = linearLayoutParams

                    val checkboxLayoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    checkboxLayoutParams.gravity = Gravity.START or Gravity.CENTER_VERTICAL
                    checkbox.layoutParams = checkboxLayoutParams

                    val editTextLayoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    editTextLayoutParams.gravity = Gravity.CENTER
                    editText.layoutParams = editTextLayoutParams

                    val textViewLayoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    textViewLayoutParams.gravity = Gravity.END or Gravity.CENTER_VERTICAL
                    textView.layoutParams = textViewLayoutParams

                    linearLayout.addView(checkbox)
                    linearLayout.addView(editText)
                    linearLayout.addView(textView)

                    ingredientLayout.addView(linearLayout)

                    checkbox.isChecked = ingredient.isChecked

                    binding.denumiriGroup.addView(ingredientLayout)

                    checkbox.setOnCheckedChangeListener { _, isChecked ->
                        ingredient.isChecked = isChecked
                        if (isChecked) {
                            ingredientLayout.setBackgroundColor(
                                ContextCompat.getColor(
                                    binding.root.context,
                                    R.color.changed
                                )
                            )
                            val quantity = editText.text.toString().toIntOrNull()
                            if (quantity != null) {
                                selectedIngredients.add(ingredient)
                            }
                        } else {
                            ingredientLayout.setBackgroundColor(
                                ContextCompat.getColor(
                                    binding.root.context,
                                    R.color.first
                                )
                            )
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
