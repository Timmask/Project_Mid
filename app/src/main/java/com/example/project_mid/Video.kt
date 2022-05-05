package com.example.project_mid

class Video {
      var videoImageName:String
    set(value) {
        if(!value.isEmpty()){
            field = value
        }

    }
    get() {
        return field
    }
      var channelImageName:String
        set(value) {
            if(!value.isEmpty()){
                field = value
            }

        }
        get() {
            return field
        }
      var videoName:String
        set(value) {
            if(!value.isEmpty()){
                field = value
            }

        }
        get() {
            return field
        }
     var channelName:String
        set(value) {
            if(!value.isEmpty()){
                field = value
            }

        }
        get() {
            return field
        }

    constructor(
        videoImageName: String,
        channelImageName: String,
        videoName: String,
        channelName: String
    ) {
        this.videoImageName = videoImageName
        this.channelImageName = channelImageName
        this.videoName = videoName
        this.channelName = channelName
    }

}