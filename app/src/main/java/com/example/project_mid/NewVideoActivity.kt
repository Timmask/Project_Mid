package com.example.project_mid

import android.app.Activity
import android.app.ProgressDialog
import android.content.*
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.project_mid.Model.Video
import com.example.project_mid.database.DBManager
import com.example.project_mid.databinding.ActivityNewVideoBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.*


class NewVideoActivity : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 71
    private var videofilePath: Uri? = null
    private var imagefilePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    lateinit var imagePreview: ImageView
    lateinit var btn_choose_image: Button
    private var progressDialog: ProgressDialog? = null
    lateinit var binding: ActivityNewVideoBinding
    var db= DBManager(null)
    var videoname:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        var namepreview: String? =null

        super.onCreate(savedInstanceState)
        binding= ActivityNewVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btn_choose_image =binding.btnChooseImage
        imagePreview = binding.imagePreview
        btn_choose_image.setOnClickListener {
             launchGallery()
             namepreview=UUID.randomUUID().toString()
        }
        binding.btnChooseVideo.setOnClickListener {
            progressDialog = ProgressDialog(this@NewVideoActivity)
            launchVideos()

        }
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference





        binding.upload.setOnClickListener {
            if( namepreview!!.isNotEmpty() && videoname!!.isNotEmpty()){
                uploadImage(namepreview!!)
//                uploadVideo(videoname!!)

                var video=Video(binding.videoname .text.toString(),
                    binding.videoDescription.text.toString(),
                    null,namepreview,
                    videoname,
                    binding.videoCategory.text.toString(),
                    0,0,0,
                    db.db.push().key)
                db.publishAd(video)
                val intent = Intent(this@NewVideoActivity,SeconActivity::class.java)
                startActivity(intent)

            }else{

            }

//https://smapse.ru/storage/2018/12/east-campus-aerial.jpg
        }
    }
    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }
    private fun launchVideos() {
        val intent = Intent()
        intent.type = "video/*"
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 5);
    }
    private fun uploadImage(name:String){
        if(imagefilePath != null){
            val ref = storageReference?.child("myImages/" + name)
            val uploadTask = ref?.putFile(imagefilePath!!)
            Log.d("log",imagefilePath.toString())
        }else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }
    private fun uploadVideo(name:String){
        if(videofilePath != null){
            val ref = storageReference?.child("myVideos/" + name)
            val uploadTask = ref?.putFile(videofilePath!!)
            Log.d("log",videofilePath.toString())
        }else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            imagefilePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imagefilePath)
                imagePreview.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else if (requestCode == 5 && resultCode == RESULT_OK && data != null && data.data != null) {
            videofilePath = data.data
            progressDialog!!.setTitle("Uploading...")
            progressDialog!!.show()
            uploadvideo()
        }
    }

    private fun uploadvideo() {
        if (videofilePath != null) {
            videoname=System.currentTimeMillis().toString() + "." + getfiletype(videofilePath!!)
            val reference = FirebaseStorage.getInstance().getReference(
                "videos/" + videoname
            )
            reference.putFile(videofilePath!!).addOnSuccessListener { taskSnapshot ->
                val uriTask = taskSnapshot.storage.downloadUrl
                while (!uriTask.isSuccessful);
                // get the link of video
                val downloadUri = uriTask.result.toString()
                val reference1 = FirebaseDatabase.getInstance().getReference("Video")
                val map = HashMap<String, String>()
                map["videolink"] = downloadUri
                reference1.child("" + System.currentTimeMillis()).setValue(map)
                // Video uploaded successfully
                // Dismiss dialog
                progressDialog!!.dismiss()
                Toast.makeText(this@NewVideoActivity, "Video Uploaded!!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { e -> // Error, Image not uploaded
                progressDialog!!.dismiss()
                Toast.makeText(this@NewVideoActivity, "Failed " + e.message, Toast.LENGTH_SHORT).show()
            }.addOnProgressListener { taskSnapshot ->
                // Progress Listener for loading
                // percentage on the dialog box
                // show the progress bar
                val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                progressDialog!!.setMessage("Uploaded " + progress.toInt() + "%")
            }
        }
    }
    private fun getfiletype(videouri: Uri): String? {
        val r = contentResolver
        // get the file type ,in this case its mp4
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(r.getType(videouri))
    }
}