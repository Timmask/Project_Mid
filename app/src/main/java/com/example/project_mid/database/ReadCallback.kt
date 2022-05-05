package com.example.project_mid.database

import com.example.project_mid.Model.Video

interface ReadCallback {
    fun readData(list: List<Video>)
}