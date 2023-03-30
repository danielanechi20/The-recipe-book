package com.example.licentaincercarea1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.fitCenter
import com.example.licentaincercarea1.databinding.ReteteItemBinding
import java.text.FieldPosition

class ReteteAdapter(val retetele : ArrayList<String>,
                    val ingrediente : ArrayList<String>,
                    val images: ArrayList<String>,
                    val pasi:ArrayList<String>) :
    RecyclerView.Adapter<ReteteAdapter.ReteteViewHolder>() {
    var listener: reteteClickListener? = null

    fun setItemClickListener(reteteListener: ReteteAdapter.reteteClickListener) {
        listener = reteteListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ReteteViewHolder {
        val binding = ReteteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReteteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReteteAdapter.ReteteViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return retetele.size
    }

    inner class ReteteViewHolder(private val binding: ReteteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val retete = retetele[adapterPosition]
            val ing=ingrediente[adapterPosition]
            val images = images[adapterPosition]
            val gatit=pasi[adapterPosition]
            binding.name.text = retete
            Glide.with(binding.root.context).load(images).fitCenter().into(binding.imagine)
            setOnItemClickListener()
        }
        private fun setOnItemClickListener() {
            binding.root.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("nume", retetele[adapterPosition])
                    putString("ingrediente",ingrediente[adapterPosition])
                    putString("imagine",images[adapterPosition])
                    putString("pasi", pasi[adapterPosition])
                }
                it.findNavController().navigate(R.id.action_categoriesFragment_to_reteteFragment, bundle)
                //listener?.onCategoryClick(retetele[adapterPosition],ingrediente[adapterPosition],
                //images[adapterPosition],pasi[adapterPosition])

            }
        }
    }
    interface reteteClickListener{
     //   fun onCategoryClick(retete:String, ing:String,images:String,gatit:String )
    }

}