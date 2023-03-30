package com.example.licentaincercarea1

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.licentaincercarea1.databinding.RetetaBinding
import kotlinx.android.synthetic.main.reteta.*
import kotlinx.android.synthetic.main.reteta.view.*


class ReteteFragment: Fragment() {
    private var _binding: RetetaBinding?=null
    private val binding get()=_binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = RetetaBinding.inflate(inflater, container, false)
        val nume = arguments?.getString("nume")
        val ingrediente = arguments?.getString("ingrediente")
        val pasi = arguments?.getString("pasi")
        val imagine = arguments?.getString("imagine")

        /*val recyclerView = findViewById<RecyclerView>(R.id.rvtext)
        recyclerView.adapter = TextAdapter(pasi) // generateData() is a function that returns a list of strings
        recyclerView.layoutManager = LinearLayoutManager(this)*/
       /* val adaptertext=TextAdapter(pasi.toString())
        binding.rvtext.apply{
            layoutManager = GridLayoutManager(requireActivity(),1)

            adapter = adaptertext
        }*/


        binding.Nume.text = nume
        binding.ingrediente.text=ingrediente
        binding.scrollView.preparare.text=pasi
        //preparare.movementMethod=ScrollingMovementMethod()
        Glide.with(binding.root.context).load(imagine).fitCenter().into(binding.imagine)
        val view=binding.root

        binding.scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = binding.scrollView.scrollY
            val maxScroll = binding.scrollView.getChildAt(0).height - binding.scrollView.height
            val progress = (scrollY.toFloat() / maxScroll.toFloat() * 100).toInt()
            binding.progressBar.progress = progress
        }


        return view
    }
}