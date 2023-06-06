package com.example.licentaincercarea1


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.licentaincercarea1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()

    }
    private fun setListener() {
        binding.button.setOnClickListener {
            startActivity(Intent(this@MainActivity,FragmentBase::class.java))
        }
    }


}