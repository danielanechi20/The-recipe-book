package com.example.licentaincercarea1

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.licentaincercarea1.data.category
import com.example.licentaincercarea1.data.ingredient
import com.example.licentaincercarea1.data.reteta
import com.example.licentaincercarea1.data.types
import com.example.licentaincercarea1.databinding.GeneratorFragment1Binding
import kotlinx.android.synthetic.main.reteta.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream


class GeneratorFragment1: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding= GeneratorFragment1Binding.inflate(inflater, container, false)

        binding.rvIngr.apply {
            layoutManager  = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = ItemAdapter(transf())
        }
        binding.next.setOnClickListener {

        }
        return binding.root
    }
    fun transf(): List<types> {
        val retete = arrayListOf<types>()
        val obj = JSONObject(loadJSONFromAsset())
        val userArray: JSONArray = obj.getJSONArray("tipuri")
        for (i in 0 until userArray.length()) {
            val userDetail = userArray.getJSONObject(i)
            val ingredienteArray = userDetail.getJSONArray("denumiri")
            val ingrediente = arrayListOf<ingredient>()
            for (j in 0 until ingredienteArray.length()) {
                val ingredienteDetail = ingredienteArray.getJSONObject(j)
                val ingredient = ingredient(
                    nume = ingredienteDetail.getString("Nume"),
                    cantitate = "0"
                )
                ingrediente.add(ingredient)
            }
            retete.add(
                types(
                    tip = userDetail.getString("tip"),
                    denumiri = ingrediente
                )
            )
        }
        return retete
    }
   /* fun transf(): List<types> {
        val types = mutableListOf<types>()
        val json = JSONObject(loadJSONFromAsset())
        val tipuriArr = json.getJSONArray("tipuri")
        for (i in 0 until tipuriArr.length()) {
            val tipObj = tipuriArr.getJSONObject(i)
            val tip = tipObj.optString("tip", null)
            if (tip != null) {
                val denumiriArr = tipObj.optJSONArray("denumiri")
                val items = mutableListOf<ingredient>()
                if (denumiriArr != null) {
                    for (j in 0 until denumiriArr.length()) {
                        val itemObj = denumiriArr.getJSONObject(j)
                        val item = ingredient(
                            nume=itemObj.optString("Nume", null),
                            cantitate="0")
                        if (item != null) {
                            items.add(item)
                        }
                    }
                }
                types.add(types(tip, items))
            }
        }
        return types
    }*/

    private fun loadJSONFromAsset(): String {
        val am = requireActivity().assets
        val inputStream: InputStream = am.open("tipuri.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        return String(buffer)
    }
}