package com.example.licentaincercarea1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.licentaincercarea1.data.category
import com.example.licentaincercarea1.databinding.CategoryItemBinding

class CategoriesAdapter(context:Context,val categories : ArrayList<String>,
                        val desc : ArrayList<String>,
                        val image: ArrayList<String>) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    var listener: CategoryClickListener? = null

    fun setCategoryClickListener(categoryListener: CategoryClickListener) {
        listener = categoryListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            CategoriesViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    inner class CategoriesViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val  category = categories[adapterPosition]
            val desc=desc[adapterPosition]
            val img=image[adapterPosition]
            binding.description.text = desc
            binding.name.text=category

            Glide.with(binding.root.context).load(img).fitCenter().into(binding.profile)
            setOnItemClickListener()
        }
        private fun setOnItemClickListener(){
            binding.root.setOnClickListener{
                listener?.onCategoryClick(categories[adapterPosition])
            }
        }
    }
    interface CategoryClickListener{
        fun onCategoryClick(category: String)
    }

}