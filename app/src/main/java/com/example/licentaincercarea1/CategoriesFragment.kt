package com.example.licentaincercarea1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.licentaincercarea1.databinding.CategoriesFragmentBinding
import kotlinx.android.synthetic.main.categories_fragment.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream


class CategoriesFragment : Fragment() {

    private var _binding: CategoriesFragmentBinding?=null
    private val binding get()=_binding!!

    var categories = ArrayList<String>()
    var desc = ArrayList<String>()
    var image = ArrayList<String>()
    private val categoriesAdapter = CategoriesAdapter(categories,desc,image)

    var retete = ArrayList<String>()
    var ingrediente = ArrayList<String>()
    var images = ArrayList<String>()
    var pasi=ArrayList<String>()
    private val vitaAdapter=VitaAdapter(retete,ingrediente,images,pasi)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = CategoriesFragmentBinding.inflate(inflater,container,false)
        val view = binding.root
        setupRv()
        transfcat(CATEGORIES)
        transfretete(VITA,"vita")
        return view
    }

    private fun transfcat(fileName: String){
        // get JSONObject from JSON file
        val obj = JSONObject(loadJSONFromAsset(fileName))
        // fetch JSONArray named colors
        val userArray: JSONArray = obj.getJSONArray("categories")
        // implement a loop to get colors list data
        for (i in 0 until userArray.length()) {
            // create a JSONObject for fetching single color data
            val userDetail = userArray.getJSONObject(i)
            // fetch color name and category and store it in arraylist
            categories.add(userDetail.getString("Name"))
            desc.add(userDetail.getString("Description"))

            image.add(userDetail.getString("Thumb"))

        }
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
            // fetch color name and category and store it in arraylist
            retete.add(userDetail.getString("Nume"))
            images.add(userDetail.getString("Thumb"))
        }
    }

    private fun loadJSONFromAsset(fileName: String): String {
        var fileText =""
        val am = requireActivity().assets
        val inputStream: InputStream = am.open(fileName)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        fileText = String(buffer)

        return fileText
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
                        binding.rvVita.apply {
                        layoutManager = LinearLayoutManager(
                            requireActivity(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        adapter = vitaAdapter
                    }}
                    ("Pui")->{  binding.rvCategories.apply {
                        layoutManager = GridLayoutManager(
                            requireActivity(),
                            3)
                        adapter = categoriesAdapter
                    }}
                    else->null
                }
            }
        })

    }


    companion object {
        const val CATEGORIES = "categories.json"
        const val VITA="vita.json"
    }
}