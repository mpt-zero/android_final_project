package com.example.final_exam

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    lateinit var registerButton: Button
    lateinit var firstName: EditText
    lateinit var lastName: EditText
    lateinit var regEmail: EditText
    lateinit var regPass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registration)


        registerButton = findViewById(R.id.registerButton)
        firstName = findViewById(R.id.firstNameInput)
        lastName = findViewById(R.id.lastNameInput)
        regEmail = findViewById(R.id.emailInput)
        regPass = findViewById(R.id.regPasswordInput)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")


        registerButton.setOnClickListener {
            if (TextUtils.isEmpty(firstName.text.toString()) || firstName.text.length > 20) {
                firstName.setError("Please Enter First Name")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(lastName.text.toString()) || lastName.text.length > 30) {
                lastName.setError("Please Enter Last Name")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(regEmail.text.toString()) || !Patterns.EMAIL_ADDRESS.matcher(regEmail.text.toString()).matches()) {
                regEmail.setError("Please Enter Email Address")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(regPass.text.toString()) || regPass.text.length > 30 || regPass.text.length < 5 || regPass.text[0].isLowerCase()) {
                regPass.setError("Please Enter Last Name")
                return@setOnClickListener
            }



            auth.createUserWithEmailAndPassword(regEmail.text.toString(), regPass.text.toString()).addOnCompleteListener() {
                if (it.isSuccessful) {
                    val currentUser = auth.currentUser
                    val currentUserDb = databaseReference?.child((currentUser?.uid!!))
                    currentUserDb?.child("firstname")?.setValue(firstName.text.toString())
                    currentUserDb?.child("lastname")?.setValue(lastName.text.toString())
                    Toast.makeText(this, "Registration Success", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}