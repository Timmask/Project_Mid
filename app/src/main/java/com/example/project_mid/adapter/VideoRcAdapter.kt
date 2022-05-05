package com.example.project_mid.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_mid.ItemClickListener
import com.example.project_mid.Model.Video
import com.example.project_mid.OnItemClickListener
import com.example.project_mid.databinding.ListItemBinding
import com.squareup.picasso.Picasso


class VideoRcAdapter: RecyclerView.Adapter<VideoRcAdapter.VideHolder>() {
    var videos=ArrayList<Video>()
     var clickListener: ItemClickListener? = null
    fun updateAdapter(newVideos:List<Video>){
        videos.clear()
        videos.addAll(newVideos)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideHolder {
        val binding=ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return VideHolder(binding)
    }

    override fun onBindViewHolder(holder: VideHolder, position: Int) {
        holder.setData(videos[position])
    }

    override fun getItemCount(): Int {
       return videos.size
    }


   inner class VideHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) ,View.OnClickListener{
        fun setData(video:Video){
            binding.apply {
                videoUid.text=video.primaryKey
                videoTitle.text=video.videoName
                textView4.text=video.accountName
                textView13.text=video.viewsCount.toString()
                Log.d("Tag","gs://qtube-61b27.appspot.com/myImages/"+video.previewImageName)
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/qtube-61b27.appspot.com/o/myImages%2F${video.previewImageName}?alt=media&token=9a171e23-4ea9-4b78-8987-ad2b9e6f7ab8").into(imageView11)
            }
        }
        override fun onClick(v: View) {

            itemView.setOnClickListener {

                if (clickListener != null)
                    clickListener!!.onClick(v, getAdapterPosition());

            }

        }

    }


}