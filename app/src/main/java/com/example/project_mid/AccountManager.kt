package com.example.project_mid

import android.widget.Toast
import com.google.firebase.auth.FirebaseUser

class AccountManager(activity:SettingsActivity) {
     private val activity=activity
        fun createNewUser(email:String,password:String): Int{
            var result=0
            if(email.isNotEmpty() && password.isNotEmpty()){
                activity.auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    task->
                    if(task.isSuccessful){
                        result=sendEmailVerification(task.result?.user!!)
                    }else{
                        Toast.makeText(activity,"not registered",Toast.LENGTH_LONG).show()
                    }

                }

            }
            return result
        }
    private fun sendEmailVerification(user:FirebaseUser): Int {
        var res=0
        user.sendEmailVerification().addOnCompleteListener {
            task->
            if(task.isSuccessful){
                Toast.makeText(activity,"на вашу почту отправлено сообщение",Toast.LENGTH_LONG).show()
                res=1
            }else{
                Toast.makeText(activity,"not registered",Toast.LENGTH_LONG).show()

            }
        }
        return res;
    }
    fun signInUser(email:String,password:String): Int{
        var result=0
        if(email.isNotEmpty() && password.isNotEmpty()){
            activity.auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    task->
                if(task.isSuccessful){
                    result=1

//                    activity.updateEmail(activity.auth.currentUser)
                }else{
                    Toast.makeText(activity,"not correct",Toast.LENGTH_LONG).show()
                }

            }

        }
        return result
    }
}