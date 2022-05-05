package com.example.project_mid.Model

import java.io.Serializable


data class Video(
    val videoName:String?=null,
    val description:String?=null,
    var accountName:String?=null,
    val previewImageName:String?=null,
    var videoFileName: String?=null,
    val category:String?=null,
    val viewsCount:Int=0,
    val likesCount:Int=0,
    val dislikesCount:Int=0,
    var primaryKey:String?=null
)
