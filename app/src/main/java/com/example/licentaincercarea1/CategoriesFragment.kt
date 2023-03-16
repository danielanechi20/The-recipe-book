package com.example.licentaincercarea1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.licentaincercarea1.databinding.CategoriesFragmentBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream


class CategoriesFragment : Fragment() {

    private var _binding: CategoriesFragmentBinding?=null
    private val binding get()=_binding!!

    var categories = ArrayList<String>()
    var desc = ArrayList<String>()
    var image = ArrayList<String>()
    private val categoriesAdapter = CategoriesAdapter(categories,desc,image)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                          savedInstanceState: Bundle?): View? {
        _binding = CategoriesFragmentBinding.inflate(inflater,container,false)
        val view = binding.root
        setupRv()
        transform()

        return view
    }
    private fun transform(){
            // get JSONObject from JSON file
            val obj = JSONObject(loadJSONFromAsset(MY_JSON))
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
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }

        categoriesAdapter.setCategoryClickListener(object:CategoriesAdapter.CategoryClickListener{
            override fun onCategoryClick(category: String) {
                when(category){
                "Beef" -> startActivity(Intent(requireActivity(),manifesting::class.java))
                else -> startActivity(Intent(requireActivity(),manifesting2::class.java))}
            }
        })

    }


    companion object {
        const val MY_JSON = "categories.json"
    }
}