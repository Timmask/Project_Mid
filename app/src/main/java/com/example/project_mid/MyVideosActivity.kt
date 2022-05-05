package com.example.project_mid

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project_mid.database.DBManager
import com.example.project_mid.databinding.ActivityMainBinding
import com.example.project_mid.databinding.ActivityMyVideosBinding


class MyVideosActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyVideosBinding
    var db=DBManager(null )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMyVideosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db.GetVideofromDb()

        binding.addimage.setOnClickListener {
            val intent = Intent(this@MyVideosActivity,NewVideoActivity::class.java)
            startActivity(intent)


        }
        binding.imageView13.setOnClickListener {
            val intent = Intent(this@MyVideosActivity,SeconActivity::class.java)
            startActivity(intent)


        }
        binding.imageView14.setOnClickListener {
            val intent = Intent(this@MyVideosActivity,SettingsActivity::class.java)
            startActivity(intent)


        }



    }
}