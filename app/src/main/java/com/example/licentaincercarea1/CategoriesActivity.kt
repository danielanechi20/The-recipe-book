package com.example.licentaincercarea1

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.licentaincercarea1.data.category
import com.example.licentaincercarea1.databinding.CategoriesScreenBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream


class CategoriesActivity : AppCompatActivity() {
    private lateinit var binding: CategoriesScreenBinding

    private var json: String = ""
    private val dataList = generateData()
    var categories = ArrayList<String>()
    var desc = ArrayList<String>()
    var image = ArrayList<String>()
    private val categoriesAdapter = CategoriesAdapter(this,categories,desc,image)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CategoriesScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRv()

        try {
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
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun loadJSONFromAsset(fileName: String): String {
        var fileText = ""
        try {
            val inputStream: InputStream = assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            fileText = String(buffer)
        } catch (error: IOException) {
            error.printStackTrace()
        }
        return fileText
    }

    private fun setupRv() {

        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(this@CategoriesActivity, LinearLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }


    }



    private fun generateData(): List<category> {

        val categoriesList = arrayListOf<category>()
        for (i in 1..50) {
            categoriesList.add(
                category(
                    Name="name",
                    Description="bla",
                    Thumb="cv")
            )
        }

        return categoriesList
    }

    companion object {
        const val MY_JSON = "categories.json"
    }
}