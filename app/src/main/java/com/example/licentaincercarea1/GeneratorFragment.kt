package com.example.licentaincercarea1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.licentaincercarea1.databinding.GeneratorFragmentBinding

class GeneratorFragment: Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: IngredientListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.generator_fragment, container, false)
        recyclerView = view.findViewById(R.id.rv_ingr)
        adapter = IngredientListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

}