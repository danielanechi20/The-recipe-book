package com.example.licentaincercarea1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.licentaincercarea1.data.category
import com.example.licentaincercarea1.databinding.CategoryItemBinding
import kotlinx.android.synthetic.main.category_item.view.*

class CategoriesAdapter(val categories: List<category>) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {

        return CategoriesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.category_item,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {

        holder.itemView.title.text = categories.get(position).name
        holder.itemView.description.text = categories.get(position).description

    }
    inner class CategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view)
}