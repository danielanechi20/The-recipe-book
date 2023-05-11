package com.example.licentaincercarea1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.licentaincercarea1.data.reteta
import com.example.licentaincercarea1.databinding.RetetaBinding
import kotlinx.android.synthetic.main.reteta.*
import kotlinx.android.synthetic.main.reteta.view.*
import org.json.JSONObject
import java.io.*

class ReteteFragment: Fragment() {
    private var _binding: RetetaBinding?=null
    private val binding get()=_binding!!
    private var numarPortii = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = RetetaBinding.inflate(inflater, container, false)
        @Suppress("DEPRECATION") val reteta = arguments?.getParcelable<reteta>("reteta")
        if (reteta != null) {
            val ingrediente = StringBuilder()
            for (ingredient in reteta.ingrediente) {
                ingrediente.append(ingredient.cantitate)
                ingrediente.append(" ")
                ingrediente.append(ingredient.nume)
                ingrediente.append("\n")
            }
            binding.Nume.text = reteta.Nume
            binding.scrollingrediente.ingrediente.text = ingrediente
            binding.scrollView.preparare.text = reteta.P
            Glide.with(binding.root.context).load(reteta.Thumb).fitCenter().into(binding.imagine)
        }
        binding.portii.text = numarPortii.toString()

        binding.scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = binding.scrollView.scrollY
            val maxScroll = binding.scrollView.getChildAt(0).height - binding.scrollView.height
            val progress = (scrollY.toFloat() / maxScroll.toFloat() * 100).toInt()
            binding.progressBar.progress = progress
        }
        binding.increase.setOnClickListener{
            binding.portii.text=(binding.portii.text.toString().toInt() + 1).toString()
            numarPortii = binding.portii.text.toString().toInt()
            if (reteta != null) {
                Actualizare(reteta)
            }
        }
        binding.decrease.setOnClickListener{
            if(binding.portii.text.toString().toInt()>1){
                binding.portii.text=(binding.portii.text.toString().toInt() - 1).toString()
                numarPortii = binding.portii.text.toString().toInt()
               // Actualizare()
            } else Toast.makeText(context, "Numarul de portii nu poate fi mai mic decat 1",Toast.LENGTH_SHORT).show()
        }
        binding.export.setOnClickListener {
           // exportReteta()
        }

        val view=binding.root
        return view
    }
    private fun Actualizare(reteta: reteta) {

        val ingrediente = StringBuilder()
        for (ingredient in reteta.ingrediente) {
            val nouaCantitate = ingredient.cantitate.toInt() * numarPortii / 1.0
            ingrediente.append(nouaCantitate.toString())
            ingrediente.append(" ")
            ingrediente.append(ingredient.nume)
            ingrediente.append("\n")
        }
        binding.scrollingrediente.ingrediente.text = ingrediente.toString()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun exportReteta() {
        val reteta = """
        Nume: ${arguments?.getString("nume") }
        
        Ingrediente:  
        
        ${arguments?.getString("ingrediente")}
        
        Mod de preparare:
 
        ${arguments?.getString("pasi")}
    """.trimIndent()

        val fileName = "${arguments?.getString("nume")}.txt"
        val file = File(requireContext().filesDir, fileName)
        file.createNewFile()
        val writer = PrintWriter(file, "UTF-8")
        writer.println(reteta)
        writer.flush()
        writer.close()

        // Deschiderea aplicatiei de email pentru a trimite fisierul text
        val fileUri = FileProvider.getUriForFile(
            requireContext(),
            requireContext().applicationContext.packageName + ".provider",
            file
        )

        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, arguments?.getString("nume"))
        emailIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivity(Intent.createChooser(emailIntent, "Trimite reteta prin:"))
    }

}