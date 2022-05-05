package com.example.project_mid


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project_mid.database.DBManager
import com.example.project_mid.databinding.ActivitySettingsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class SettingsActivity: AppCompatActivity() {

    companion object {
        const val USERN = "user"
    }

    lateinit var binding: ActivitySettingsBinding
    var auth= FirebaseAuth.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateUsername()
        var add=binding.navView.menu.findItem(R.id.liked)
        add.setOnMenuItemClickListener{
//            db.publishAd()
            false
        }
        binding.imageView13.setOnClickListener {
            val intent = Intent(this@SettingsActivity, SeconActivity::class.java)

            startActivity(intent)

        }
        binding.myvideos.setOnClickListener {
            val intent = Intent(this@SettingsActivity, MyVideosActivity::class.java)

            startActivity(intent)

        }
        var  mi:MenuItem = binding.navView.menu.findItem(com.example.project_mid.R.id.singin)
        mi.setOnMenuItemClickListener {
            val intent = Intent(this@SettingsActivity, LoginActivity::class.java)

            startActivity(intent)
            false
        }
        var  mr:MenuItem = binding.navView.menu.findItem(com.example.project_mid.R.id.signup)
        mr.setOnMenuItemClickListener {
            val intent = Intent(this@SettingsActivity, RegisterActivity::class.java)
            startActivity(intent)
            false
        }
        var  mo:MenuItem = binding.navView.menu.findItem(com.example.project_mid.R.id.signout)
        mo.setOnMenuItemClickListener {
           auth.signOut()
            mo.isVisible=false
            mr.isVisible=true
            mi.isVisible=true;
            false
        }
    }
    fun updateUsername(){
        var username = intent.getStringExtra(USERN)
        var un=binding.navView.getHeaderView(0).findViewById<TextView>(R.id.usernameView)
        if(username.equals("user") || username==null){
            un.setText("not authorized")
            binding.navView.menu.findItem(com.example.project_mid.R.id.signout).isVisible=false
            binding.navView.menu.findItem(com.example.project_mid.R.id.signup).isVisible=true
            binding.navView.menu.findItem(com.example.project_mid.R.id.singin).isVisible=true;
        }else{
            un.setText(username)
            binding.navView.menu.findItem(com.example.project_mid.R.id.signout).isVisible=true
            binding.navView.menu.findItem(com.example.project_mid.R.id.signup).isVisible=false
            binding.navView.menu.findItem(com.example.project_mid.R.id.singin).isVisible=false;

        }
    }
}


