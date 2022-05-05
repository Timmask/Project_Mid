package com.example.project_mid

import com.example.project_mid.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.iterator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_mid.Model.Video
import com.example.project_mid.adapter.VideoRcAdapter
import com.example.project_mid.database.DBManager
import com.example.project_mid.database.ReadCallback
import com.example.project_mid.databinding.ActivitySecondBinding
import com.google.android.material.navigation.NavigationView



class SeconActivity:AppCompatActivity(),ReadCallback ,ItemClickListener{
    lateinit var binding:ActivitySecondBinding
    var dbManager=DBManager(this)
     var videoRcAdapter=VideoRcAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySecondBinding.inflate(layoutInflater)
        videoRcAdapter.clickListener=this
        setContentView(binding.root)
        dbManager.GetVideofromDb()
        var recyclerView:RecyclerView=binding.recycler
        recyclerView.layoutManager=LinearLayoutManager(this@SeconActivity)

        recyclerView.adapter=videoRcAdapter

        binding.imageView14.setOnClickListener {
            val intent=Intent(this@SeconActivity,SettingsActivity::class.java)
            startActivity(intent)
        }
        binding.myvideos.setOnClickListener {
            val intent=Intent(this@SeconActivity,MyVideosActivity::class.java)
            startActivity(intent)
        }
//        Log.d("ID",binding.recycler.get(0).id.toString())
        binding.imageView15.setOnClickListener{
            var intent=Intent(this@SeconActivity,VideoActivity::class.java)

            intent.putExtra(VideoActivity.VIDEOUID,"N1AD8Jo3A8OBzSRKWmI")
            binding.root.context.startActivity(intent)
        }

        }

    override fun readData(list: List<Video>) {
        videoRcAdapter.updateAdapter(list)
    }

    override fun onClick(view: View?, position: Int) {
        var intent=Intent(this@SeconActivity,VideoActivity::class.java)

        intent.putExtra(VideoActivity.VIDEOUID,"N1AD8Jo3A8OBzSRKWmI")
        binding.root.context.startActivity(intent)
    }


}




/*


Критерии по мобилке
Логин
Уведомление
Меню
Жизненный цикл
Сортировка
Поиск
 */