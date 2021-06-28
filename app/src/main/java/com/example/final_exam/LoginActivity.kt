package com.example.final_exam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var userEmailInput : EditText
    lateinit var userPasswordInput : EditText
    lateinit var loginButton : Button
    lateinit var registerLink : TextView
    lateinit var forgotPass : Button
    lateinit var view : View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        userEmailInput = findViewById(R.id.userEmailInput)
        userPasswordInput = findViewById(R.id.userPasswordInput)
        loginButton = findViewById(R.id.loginButton)
        registerLink = findViewById(R.id.register_text)
        forgotPass = findViewById(R.id.forgotPass)
        val currentUser = auth.currentUser
        if (currentUser!= null){
            startActivity(Intent(this,MainActivity::class.java))
        }
        login()
        registerLink.setOnClickListener{
            startActivity(Intent(this,RegistrationActivity::class.java))
        }
        forgotPass.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Forgot Password")
             view = layoutInflater.inflate(R.layout.dialog_forgot_password,null)
            builder.setView(view)
            builder.setPositiveButton("Reset",{ _, _ ->
                forgotPassword(userEmailInput)
            })
            builder.setNegativeButton("Cancel",{ _, _ ->})
            builder.show()
        }


    }

    private fun forgotPassword(currUserEmail : EditText){
        if(currUserEmail.text.toString().isEmpty()){
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(currUserEmail.text.toString()).matches()){
            return
        }

        auth.sendPasswordResetEmail(currUserEmail.text.toString()).
                addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this,"Email sent",Toast.LENGTH_LONG).show()
                    }
                }

    }

    private fun login(){
        loginButton.setOnClickListener {
            if(TextUtils.isEmpty(userEmailInput.text.toString())){
                userEmailInput.setError("Please Enter Valid Email Address")
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(userPasswordInput.text.toString())){
                userPasswordInput.setError("Please Enter Valid Password")
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(userEmailInput.text.toString(),userPasswordInput.text.toString()).
            addOnCompleteListener{
                if(it.isSuccessful){
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this,"Login Failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}