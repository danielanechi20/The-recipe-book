package com.example.licentaincercarea1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.licentaincercarea1.data.ingredient
import com.example.licentaincercarea1.data.reteta
import com.example.licentaincercarea1.databinding.FavouriteFragmentBinding
import com.example.licentaincercarea1.databinding.IntrebariFragmentBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

class IntrebariFragment: Fragment() {
    data class Intrebare(
        val text: String,
        val raspunsAdevaratIndex: Int,
        val raspunsFalsIndex: Int
    )
    private val intrebari: MutableList<Intrebare> = mutableListOf(
        Intrebare("Ai pofta de ceva dulce?", 1, 2),
        Intrebare("Poate ceva cu ciocolata?", 2, 3),
        Intrebare("Ai pofta de ceva ce contine carne?", 3, 0), // La a treia întrebare se revine la prima
        Intrebare("Atunci ceva cu fructe?", 0, 0) // La a patra întrebare se revine la prima
    )

    private var indexIntrebareCurenta = 0
    private lateinit var intrebareCurenta: Intrebare

    private var _binding: IntrebariFragmentBinding?=null
    private val binding get()=_binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = IntrebariFragmentBinding.inflate(inflater, container, false)
        intrebareCurenta = intrebari[indexIntrebareCurenta]
        binding.intrebare = intrebareCurenta

        binding.raspunsTrueButton.setOnClickListener {
            verificaRaspuns(true)
        }

        binding.raspunsFalseButton.setOnClickListener {
            verificaRaspuns(false)
        }

        return binding.root

    }
    private fun verificaRaspuns(raspunsUtilizator: Boolean) {
        if (raspunsUtilizator) {
            indexIntrebareCurenta = intrebareCurenta.raspunsAdevaratIndex
            actualizareLista(indexIntrebareCurenta, true)
        } else {
            indexIntrebareCurenta = intrebareCurenta.raspunsFalsIndex
            actualizareLista(indexIntrebareCurenta, false)
        }

        if (indexIntrebareCurenta == 0) {
            val bundle = Bundle().apply {
                putParcelableArrayList("retete", ArrayList(retete))
            }
            binding.root.findNavController().navigate(R.id.action_intrebariFragment_to_rezultatFragment,bundle)
        } else {
            intrebareCurenta = intrebari[indexIntrebareCurenta]
            binding.intrebare = intrebareCurenta
        }
    }

    private val retete: MutableList<reteta> = mutableListOf()

    private fun actualizareLista(indexIntrebare: Int, adauga: Boolean) {
        when (indexIntrebare) {
            1 -> {
                if (adauga) {
                    retete.addAll(transfretete("desert.json", "desert"))
                } //else {
                    //retete.removeAll(transfretete("desert.json", "desert"))
               // }
            }
            3 -> {
                if (adauga) {
                    retete.addAll(transfretete("pui.json", "pui"))
                } else {
                    retete.removeAll(transfretete("pui.json", "pui"))
                }
            }
            // Adăugați alte cazuri pentru diferite indexuri și listă de rețete
        }
    }




    fun transfretete(fileName: String, name: String): List<reteta> {
        val retete = arrayListOf<reteta>()
        val obj = JSONObject(loadJSONFromAsset(fileName))
        val userArray: JSONArray = obj.getJSONArray(name)
        for (i in 0 until userArray.length()) {
            val userDetail = userArray.getJSONObject(i)
            val ingredienteArray = userDetail.getJSONArray("In")
            val ingrediente = arrayListOf<ingredient>()
            for (j in 0 until ingredienteArray.length()) {
                val ingredienteDetail = ingredienteArray.getJSONObject(j)
                val ingredient = ingredient(
                    nume = ingredienteDetail.getString("Nume"),
                    cantitate = 0,
                    masura = ingredienteDetail.getString("Masura")
                )
                ingrediente.add(ingredient)
            }
            retete.add(
                reteta(
                    id = "$i",
                    Nume = userDetail.getString("Nume"),
                    Thumb = userDetail.getString("Thumb"),
                    ingrediente = ingrediente,
                    P = userDetail.getString("P")
                )
            )
        }
        return retete
    }
    private fun loadJSONFromAsset(fileName: String): String {
        val am = requireActivity().assets
        val inputStream: InputStream = am.open(fileName)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        return String(buffer)
    }
}