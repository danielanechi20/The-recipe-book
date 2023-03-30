package com.example.licentaincercarea1

import android.content.Intent
import android.os.Bundle
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
import com.example.licentaincercarea1.databinding.CategoriesFragmentBinding
import kotlinx.android.synthetic.main.categories_fragment.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream


class CategoriesFragment : Fragment() {

    private var _binding: CategoriesFragmentBinding?=null
    private val binding get()=_binding!!

    private var categories = ArrayList<String>()
    private var desc = ArrayList<String>()
    private var image = ArrayList<String>()
    private val categoriesAdapter = CategoriesAdapter(categories,desc,image)

    private var retete = ArrayList<String>()
    private var ingrediente = ArrayList<String>()
    private var images = ArrayList<String>()
    private var pasi=ArrayList<String>()




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = CategoriesFragmentBinding.inflate(inflater,container,false)
        val view = binding.root
        setupRv()
        transfcat()
        return view
    }

    private fun transfcat(){
        // get JSONObject from JSON file
        val obj = JSONObject(loadJSONFromAsset(CATEGORIES))
        // fetch JSONArray named colors
        val userArray: JSONArray = obj.getJSONArray("categories")
        // implement a loop to get colors list data
        for (i in 0 until userArray.length()) {
            // create a JSONObject for fetching single color data
            val userDetail = userArray.getJSONObject(i)
            categories.add(userDetail.getString("Name"))
            desc.add(userDetail.getString("Description"))
            image.add(userDetail.getString("Thumb"))

        }
    }
    private fun close(){
        retete.clear()
        images.clear()
        ingrediente.clear()
        pasi.clear()
    }
    private fun transfretete(fileName: String, name:String){
        // get JSONObject from JSON file
        val obj = JSONObject(loadJSONFromAsset(fileName))
        // fetch JSONArray named colors
        val userArray: JSONArray = obj.getJSONArray(name)
        // implement a loop to get colors list data
        for (i in 0 until userArray.length()) {
            // create a JSONObject for fetching single color data
            val userDetail = userArray.getJSONObject(i)
            retete.add(userDetail.getString("Nume"))
            images.add(userDetail.getString("Thumb"))
            ingrediente.add(userDetail.getString("In"))
            pasi.add(userDetail.getString("P"))
        }
    }

    private fun loadJSONFromAsset(fileName: String): String {
        val am = requireActivity().assets
        val inputStream: InputStream = am.open(fileName)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        return String(buffer)

    }
    private val reteteAdapter=ReteteAdapter(retete,ingrediente,images,pasi)
    private fun setuprvRetete(){
        binding.rvRetete.apply {
            layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = reteteAdapter
        }

    }
    private fun setupRv() {

        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = categoriesAdapter
        }

        categoriesAdapter.setCategoryClickListener(object:CategoriesAdapter.CategoryClickListener{
            override fun onCategoryClick(category: String) {
                when (category) {
                    ("Vita")->{ rv_categories.isVisible=false
                        close()
                        transfretete(VITA,"vita")
                        setuprvRetete()
                    }
                    ("Pui")->{ rv_categories.isVisible=false
                        close()
                        transfretete(PUI,"pui")
                       setuprvRetete()
                    }
                    ("Mic dejun")->{ rv_categories.isVisible=false
                        close()
                        transfretete(DEJUN,"dejun")
                        setuprvRetete()
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