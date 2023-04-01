package com.example.licentaincercarea1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.licentaincercarea1.databinding.FavouriteFragmentBinding
import com.example.licentaincercarea1.databinding.RetetaBinding
import kotlinx.android.synthetic.main.reteta.*

class FavouriteFragment: Fragment() {
    private var _binding: FavouriteFragmentBinding?=null
    private val binding get()=_binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FavouriteFragmentBinding.inflate(inflater, container, false)
        val selectedItems = mutableListOf<String>()



        return view
    }



}