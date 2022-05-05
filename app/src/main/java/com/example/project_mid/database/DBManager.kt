package com.example.project_mid.database

import android.util.Log
import com.example.project_mid.Model.Video
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DBManager(val readCallback: ReadCallback?) {
    val db= Firebase.database.getReference("main")

    val auth= Firebase.auth
    fun publishAd(video:Video){
        video.accountName=auth.currentUser?.email
        db.child(video.primaryKey?:"empty").child(auth.uid?:"admin").setValue(video)

    }
    fun GetVideofromDb(): ArrayList<Video> {
        var videos: ArrayList<Video> =ArrayList<Video>()

        db.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                for(item in snapshot.children){
//                    if(item.children=auth.uid)
                    var video=item.children.iterator().next().getValue(Video::class.java)
                    if(video!=null)videos.add(video)
                    Log.d("tag",video?.videoName.toString())
                }
                readCallback?.readData(videos)

            }

            override fun onCancelled(error: DatabaseError) {}

        })
    return videos
    }

    fun getItem(uid:String):Video{
        var video=Video()
        db.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {



                    var video=snapshot.child(uid).getValue(Video::class.java)
                    Log.d("Log", video.toString())
//                        .children.iterator().next().getValue(Video::class.java)




            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


        return video
    }
}