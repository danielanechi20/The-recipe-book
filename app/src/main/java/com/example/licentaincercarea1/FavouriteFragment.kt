package com.example.licentaincercarea1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.licentaincercarea1.data.ingredient
import com.example.licentaincercarea1.data.reteta
import com.example.licentaincercarea1.databinding.FavouriteFragmentBinding
import com.example.licentaincercarea1.databinding.GeneratorFragment1Binding
import com.example.licentaincercarea1.databinding.RetetaBinding
import kotlinx.android.synthetic.main.reteta.*

class FavouriteFragment: Fragment() {
    private var _binding: FavouriteFragmentBinding?=null
    private val binding get()=_binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding= FavouriteFragmentBinding.inflate(inflater, container, false)
        @Suppress("DEPRECATION") val ingrediente = arguments?.getParcelableArrayList<ingredient>("informatii")
        if (ingrediente != null) {
            // concatenarea textului pentru fiecare ingredient
            val stringBuilder = StringBuilder()
            for (ingredient in ingrediente) {
                stringBuilder.append("${ingredient.nume} - ${ingredient.cantitate}\n")

            }
            stringBuilder.append("Acesta este primul ingredient introdus")
            // afișarea textului într-un TextView
            binding.test.text = stringBuilder.toString()
        }
        else
            binding.test.text ="esuat"
        return binding.root
    }
}