package com.example.licentaincercarea1

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.licentaincercarea1.databinding.AdvancedSearchFragmentBinding
import com.example.licentaincercarea1.databinding.RetetaBinding
import kotlinx.android.synthetic.main.activity_main.*

class AdvancedSearchFragment :Fragment() {
    private var _binding: AdvancedSearchFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = AdvancedSearchFragmentBinding.inflate(inflater, container, false)
        binding.casa.setOnClickListener {
            it.findNavController().navigate(R.id.action_advanced_to_generatorFragment)
        }
        binding.chef.setOnClickListener{
            it.findNavController().navigate(R.id.action_advanced_to_intrebariFragment)
        }
        val view=binding.root
        return view
    }
}