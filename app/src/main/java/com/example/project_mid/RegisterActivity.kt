package com.example.project_mid



import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.example.project_mid.databinding.ActivityLoginBinding
import com.example.project_mid.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegisterActivity() : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    var auth=FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            var login=binding.editTextTextPersonName.text
            var pas=binding.editTextTextPassword.text
            var count=0
            for(let in login){
                if(let=='!' || let=='*' || let=='$' || let=='%' || let=='&'){
                    count++;
                }
            }

            if( login.isEmpty() or pas.isEmpty()){

                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder
                    .setTitle("Error:")
                    .setMessage("Fields are empty ")
                    .setCancelable(false)
                    .setPositiveButton("ok",DialogInterface.OnClickListener{dialog,id->dialog.cancel() })


                val alert = builder.create()
                alert.show()

            }else if(pas.length<8 || count>0  ){
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder
                    .setTitle("Incorrect data :")
                    .setMessage("Password must be more than 8 letters \nLogin can't contain:!#$%& ")
                    .setCancelable(false)
                    .setPositiveButton("ok",DialogInterface.OnClickListener{dialog,id->dialog.cancel() })


                val alert = builder.create()
                alert.show()
            } else{
               var choice= createNewUser(login.toString(),pas.toString())

                if(choice==1){

                    val intent = Intent(this@RegisterActivity,SeconActivity::class.java)
                    startActivity(intent)
                }

            }




        }


    }

    fun createNewUser(email:String,password:String): Int{
        var result=0
        if(email.isNotEmpty() && password.isNotEmpty()){
            this.auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    task->
                if(task.isSuccessful){
                    result=sendEmailVerification(task.result?.user!!)
                }else{
                    Toast.makeText(this,"not registered", Toast.LENGTH_LONG).show()
                }

            }

        }
        return result
    }
    private fun sendEmailVerification(user: FirebaseUser): Int {
        var res=0
        user.sendEmailVerification().addOnCompleteListener {
                task->
            if(task.isSuccessful){
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder
                    .setTitle("info:")
                    .setMessage("На вашу почту было отправлено сообщение для подтверждения почты ")
                    .setCancelable(false)
                    .setPositiveButton("ok",DialogInterface.OnClickListener{
                            dialog,id->dialog.cancel()
                        val intent = Intent(this@RegisterActivity,SeconActivity::class.java)
                        intent.putExtra(SettingsActivity.USERN,auth.currentUser?.email.toString())
                        startActivity(intent)
                         })


                val alert = builder.create()
                alert.show()
                res=1
            }else{
                Toast.makeText(this,"not registered",Toast.LENGTH_LONG).show()

            }
        }
        return res;
    }

}
