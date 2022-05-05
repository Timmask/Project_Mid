package com.example.project_mid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import com.example.project_mid.database.DBManager
import com.example.project_mid.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {
    lateinit var binding:ActivityVideoBinding
    lateinit var mediaController:MediaController
    companion object {
        const val VIDEOUID = "N1AD8Jo3A8OBzSRKWmI"
    }

    var dbManager=DBManager(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityVideoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adapter()
    }
    fun adapter(){
        var video=dbManager.getItem(VIDEOUID)
        Log.d("Tag", VIDEOUID)

        binding.apply {

            video.videoFileName="1651603083300.mp4"
            videoTitle.text=video.videoName
            textView4.text=video.accountName
            textView13.text=video.viewsCount.toString()
            Log.d("Tag","https://firebasestorage.googleapis.com/v0/b/qtube-61b27.appspot.com/o/videos%2F${video.videoFileName}?alt=media&token=49c68cd3-a7c2-4e8c-b02b-c03ade94796f")


            videoView.setVideoPath("https://firebasestorage.googleapis.com/v0/b/qtube-61b27.appspot.com/o/videos%2F${video.videoFileName}?alt=media&token=49c68cd3-a7c2-4e8c-b02b-c03ade94796f")
            mediaController = MediaController(this@VideoActivity)
            mediaController.setAnchorView(videoView)
            videoView.setMediaController(mediaController)
            videoView.start()
        }
    }

}