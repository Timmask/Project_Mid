package com.example.project_mid

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project_mid.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.imageView.setOnClickListener {
            val intent = Intent(this@MainActivity,SeconActivity::class.java)
            startActivity(intent)
            var video=Video("bdw","jsdnvj","Moiqnd","spkdncv")


        }





    }
}