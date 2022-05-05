package com.example.project_mid

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


class new : AppCompatActivity() {
    var uploadv: Button? = null
    var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialise layout

    }

    // choose a video from phone storage
    private fun choosevideo() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 5)
    }

    var videouri: Uri? = null

    // startActivityForResult is used to receive the result, which is the selected video.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 5 && resultCode == RESULT_OK && data != null && data.data != null) {
            videouri = data.data
            progressDialog!!.setTitle("Uploading...")
            progressDialog!!.show()
            uploadvideo()
        }
    }

    private fun getfiletype(videouri: Uri): String? {
        val r = contentResolver
        // get the file type ,in this case its mp4
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(r.getType(videouri))
    }

    private fun uploadvideo() {
        if (videouri != null) {
            // save the selected video in Firebase storage
            val reference = FirebaseStorage.getInstance().getReference(
                "Files/" + System.currentTimeMillis() + "." + getfiletype(
                    videouri!!
                )
            )
            reference.putFile(videouri!!).addOnSuccessListener { taskSnapshot ->
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

            }.addOnFailureListener { e -> // Error, Image not uploaded
                progressDialog!!.dismiss()

            }.addOnProgressListener { taskSnapshot ->
                // Progress Listener for loading
                // percentage on the dialog box
                // show the progress bar
                val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                progressDialog!!.setMessage("Uploaded " + progress.toInt() + "%")
            }
        }
    }
}
