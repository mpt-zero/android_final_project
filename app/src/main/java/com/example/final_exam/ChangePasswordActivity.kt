package com.example.final_exam

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import okhttp3.internal.Util

class ChangePasswordActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var currPass : EditText
    lateinit var newPass : EditText
    lateinit var confirmPass : EditText
    lateinit var changeBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        auth = FirebaseAuth.getInstance()
        currPass = findViewById(R.id.current_pass)
        newPass = findViewById(R.id.new_pass)
        confirmPass = findViewById(R.id.confirm_pass)
        changeBtn = findViewById(R.id.btn_change_pass)


        changeBtn.setOnClickListener {
            changePassword()
        }
    }

    private fun changePassword(){

        if(currPass.text.toString().isNotEmpty()&&
                newPass.text.toString().isNotEmpty() &&
                    confirmPass.text.toString().isNotEmpty()
                ){
                    if(newPass.text.toString().equals(confirmPass.text.toString())){
                        val user = auth.currentUser
                        if(user != null && user.email!=null){
                            val credential = EmailAuthProvider.getCredential(
                                user.email!!,currPass.text.toString())

                            user?.reauthenticate(credential)?.addOnCompleteListener {
                                if (it.isSuccessful){
                                    Toast.makeText(this,"Re-Authentication Success",Toast.LENGTH_LONG).show()

                                    user?.updatePassword(newPass.text.toString())
                                        .addOnCompleteListener {
                                            if(it.isSuccessful){
                                                Toast.makeText(this,"Re-Authentication Success",Toast.LENGTH_LONG).show()
                                                auth.signOut()
                                                startActivity(Intent(this,LoginActivity::class.java))
                                                finish()
                                            }
                                        }


                                }else{
                                    Toast.makeText(this,"Re-Authentication Failed",Toast.LENGTH_LONG).show()
                                }
                            }

                        }else{
                            startActivity(Intent(this,LoginActivity::class.java))
                            finish()
                        }
                    }else{
                        Toast.makeText(this,"Password Mismatching",Toast.LENGTH_LONG).show()
                    }
        }
        else{
            Toast.makeText(this,"Please Enter All The Field",Toast.LENGTH_LONG).show()
        }

    }
}