package com.example.licentaincercarea1

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.licentaincercarea1.databinding.RetetaBinding
import kotlinx.android.synthetic.main.reteta.*
import kotlinx.android.synthetic.main.reteta.view.*
import org.json.JSONObject
import java.io.*
import java.nio.charset.Charset


class ReteteFragment: Fragment() {
    private var _binding: RetetaBinding?=null
    private val binding get()=_binding!!
    private var numarPortii = 1
    private var ingrediente = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = RetetaBinding.inflate(inflater, container, false)
        val nume = arguments?.getString("nume")
        ingrediente = arguments?.getString("ingrediente") ?: ""
        val pasi = arguments?.getString("pasi")
        val imagine = arguments?.getString("imagine")

        binding.Nume.text = nume
        binding.scrollingrediente.ingrediente.text=ingrediente
        binding.scrollView.preparare.text=pasi
        Glide.with(binding.root.context).load(imagine).fitCenter().into(binding.imagine)

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
            Actualizare()
        }
        binding.decrease.setOnClickListener{
            if(binding.portii.text.toString().toInt()>1){
                binding.portii.text=(binding.portii.text.toString().toInt() - 1).toString()
                numarPortii = binding.portii.text.toString().toInt()
                Actualizare()
            } else Toast.makeText(context, "Numarul de portii nu poate fi mai mic decat 1",Toast.LENGTH_SHORT).show()
        }
        binding.export.setOnClickListener {
            exportReteta()
        }

        val view=binding.root
        return view
    }
    private fun Actualizare() {
        val ingrediente = ingrediente.split("\n")
        val sb = StringBuilder()

        for (ingred in ingrediente) {
            val parts = ingred.split(" ")
            if (parts.size > 1) {
                val cantitate = parts[0].toInt()
                val unitate = parts[1]

                // ajustam cantitatea
                val nouaCantitate = cantitate * numarPortii / 1.0
                sb.append(nouaCantitate.toString())
                sb.append(" ")
                sb.append(unitate)
                sb.append(" ")
                // adaugam restul stringului
                for (i in 2 until parts.size) {
                    sb.append(parts[i])
                    sb.append(" ")
                }
                sb.append("\n")
            } else {
                sb.append(ingred)
                sb.append("\n")
            }
        }

        binding.scrollingrediente.ingrediente.text = sb.toString()
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