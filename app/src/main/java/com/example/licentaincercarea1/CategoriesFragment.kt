package com.example.licentaincercarea1

import android.content.Intent
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.createNavigateOnClickListener
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.licentaincercarea1.data.category
import com.example.licentaincercarea1.data.ingredient
import com.example.licentaincercarea1.data.reteta
import com.example.licentaincercarea1.databinding.CategoriesFragmentBinding
import kotlinx.android.synthetic.main.categories_fragment.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream


class CategoriesFragment : Fragment() {

    private var _binding: CategoriesFragmentBinding?=null
    private val binding get()=_binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = CategoriesFragmentBinding.inflate(inflater,container,false)
        val view = binding.root
        val categorii=transfcat()
        val categoriesAdapter = CategoriesAdapter(categorii)
        setupRv(categoriesAdapter)
        binding.toate.setOnClickListener {
            it.findNavController().navigate(R.id.action_categoriesFragment_to_manifesting)
        }
        return view
    }

    private fun transfcat():List<category>{
        val categoryList=arrayListOf<category>()
        val obj = JSONObject(loadJSONFromAsset(CATEGORIES))
        val userArray: JSONArray = obj.getJSONArray("categories")
        for (i in 0 until userArray.length()) {
            val userDetail = userArray.getJSONObject(i)
            categoryList.add(
                category(
                    id = "$i",
                    Name = userDetail.getString("Name"),
                    Thumb = userDetail.getString("Thumb"),
                    Description = userDetail.getString("Description")
                )
            )
        }
        return categoryList
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
                    cantitate = ingredienteDetail.getInt("Cantitate"),
                    masura=ingredienteDetail.getString("Masura")
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

    private fun setuprvRetete(adapterr: ReteteAdapter){
        binding.rvRetete.apply {
            layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = adapterr
        }

    }
    private fun setupRv(adapterc: CategoriesAdapter) {

        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = adapterc
        }

        adapterc.setCategoryClickListener(object:CategoriesAdapter.CategoryClickListener{
            override fun onCategoryClick(category: category) {
                when (category.Name) {
                    ("Vita")->{ rv_categories.isVisible=false
                        toate.isVisible=false
                        val vitaadapter=ReteteAdapter(transfretete(VITA,"vita"))
                        setuprvRetete(vitaadapter)
                    }
                    ("Pui")->{ rv_categories.isVisible=false
                        toate.isVisible=false
                        val pui=transfretete(PUI,"pui")
                        val puiadapter=ReteteAdapter(pui)
                       setuprvRetete(puiadapter)
                    }
                    ("Mic dejun")->{ rv_categories.isVisible=false
                        toate.isVisible=false
                        val dejun=transfretete(DEJUN,"dejun")
                        val dejunadapter=ReteteAdapter(dejun)
                        setuprvRetete(dejunadapter)
                    }
                    else->null
                }
            }
        })

    }


    companion object {
        const val CATEGORIES = "categories.json"
        const val VITA="vita.json"
        const val PUI="pui.json"
        const val DEJUN="dejun.json"
    }
}