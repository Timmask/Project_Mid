package com.example.project_mid

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project_mid.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity() : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    var auth= FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button2.setOnClickListener {
            val email=binding.editTextTextPersonName.text.toString()
            if(email.isEmpty()){
                binding.textView2.visibility= View.VISIBLE

            }else{
                auth.sendPasswordResetEmail(email).addOnCompleteListener {
                    task->
                    if(task.isSuccessful){
                        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                        builder
                            .setTitle("Info ")
                            .setMessage("На вашу почту было отправлено сообщение с ссылкой для создания нового пароля")
                            .setCancelable(false)
                            .setPositiveButton("ok",DialogInterface.OnClickListener{dialog,id->dialog.cancel() })

                        val alert = builder.create()
                        alert.show()
                    }else{
                        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                        builder
                            .setTitle("Info ")
                            .setMessage("Введите корректную почту")
                            .setCancelable(false)
                            .setPositiveButton("ok",DialogInterface.OnClickListener{dialog,id->dialog.cancel() })

                        val alert = builder.create()
                        alert.show()
                    }
                }

            }

        }
//qwerty
        binding.textView5.setOnClickListener {
            val intent = Intent(this@LoginActivity,SettingsActivity::class.java)

            startActivity(intent)

        }
        binding.button.setOnClickListener {
            val email=binding.editTextTextPersonName.text.toString()
            val pass=binding.editTextTextPassword.text.toString()
            val res=signInUser(email,pass)
            print(pass+" message")

        }

    }
    fun signInUser(email:String,password:String): Int{
        var result=0
        if(email.isNotEmpty() && password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    task->
                if(task.isSuccessful){
                    result=1
                    val intent = Intent(this@LoginActivity,SettingsActivity::class.java)
                    intent.putExtra(SettingsActivity.USERN,auth.currentUser?.email.toString())
                    startActivity(intent)
//                    this.updateEmail(this.auth.currentUser)
                }else{
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder
                        .setTitle("Error:")
                        .setMessage("Incorrect email or password "+password)
                        .setCancelable(false)
                        .setPositiveButton("ok",DialogInterface.OnClickListener{dialog,id->dialog.cancel() })

                    val alert = builder.create()
                    alert.show()
                }

            }

        }
        return result
    }
}
