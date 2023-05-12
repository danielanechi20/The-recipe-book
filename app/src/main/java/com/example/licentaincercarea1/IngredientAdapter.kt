package com.example.licentaincercarea1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.licentaincercarea1.data.ingredient

class IngredientListAdapter : RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder>() {
    private val ingredientList = mutableListOf<ingredient>()

    fun setData(newList: List<ingredient>) {
        ingredientList.clear()
        ingredientList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingr_item, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredientList[position]
        holder.ingredientName.setText(ingredient.nume)
        holder.ingredientQuantity.setText(ingredient.cantitate)
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }
    inner class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ingredientName: EditText = itemView.findViewById(R.id.ingredient_name)
        val ingredientQuantity: EditText = itemView.findViewById(R.id.ingredient_quantity)
    }
}

