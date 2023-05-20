package com.example.licentaincercarea1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.licentaincercarea1.data.ingredient
import com.example.licentaincercarea1.data.reteta
import com.example.licentaincercarea1.databinding.GeneratorFragment2Binding
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

class GeneratorFragment2: Fragment() {
    private var _binding: GeneratorFragment2Binding?=null
    private val binding get()=_binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding= GeneratorFragment2Binding.inflate(inflater, container, false)
        val bundle = arguments
        @Suppress("DEPRECATION") val selectedIngredients = bundle?.getParcelableArrayList<ingredient>("ingred")
        if(selectedIngredients.isNullOrEmpty())
            binding.test.text="problee"
        val recipes = mutableListOf<reteta>()
        recipes.addAll(transfretete("dejun.json", "dejun"))
        recipes.addAll(transfretete("pui.json", "pui"))
        if (generateMatchingRecipes(selectedIngredients,recipes).isEmpty())
        { binding.test.text="problee"}
        else{
        binding.rvRetete.apply {
            layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = ReteteAdapter(generateMatchingRecipes(selectedIngredients,recipes))
        }}
        return binding.root
    }
    fun generateMatchingRecipes(ingredientList: ArrayList<ingredient>?, recipeList: MutableList<reteta>): List<reteta> {
        val matchingRecipes = mutableListOf<reteta>()

        for (recipe in recipeList) {
            val matchingIngredients = recipe.ingrediente.filter { recipeIngredient ->
                ingredientList!!.any { ingredient ->
                    ingredient.nume == recipeIngredient.nume &&
                            ingredient.cantitate.toIntOrNull() != null &&
                            recipeIngredient.cantitate.toIntOrNull() != null &&
                            ingredient.cantitate.toInt() in (recipeIngredient.cantitate.toInt() - (recipeIngredient.cantitate.toInt() / 2))..(recipeIngredient.cantitate.toInt() + (recipeIngredient.cantitate.toInt() / 2))
                }
            }
            if(matchingIngredients.isNotEmpty()) {
                Log.d("tagg", "s u gasit ingrediente care se potrivesc")
            }
            matchingRecipes.add(recipe)
            // Verificăm dacă numărul de ingrediente potrivite găsite în rețetă
            // este mai mare sau egal cu 3/4 din numărul total de ingrediente din rețetă
            /*if (matchingIngredients.size >= (recipe.ingrediente.size * 3 / 4) && matchingIngredients.size > 0) {
                matchingRecipes.add(recipe)
            }*/
        }

        return matchingRecipes
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
                    cantitate = ingredienteDetail.getString("Cantitate")
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