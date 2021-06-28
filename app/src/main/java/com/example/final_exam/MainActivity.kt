package com.example.final_exam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database : FirebaseDatabase? = null
    lateinit var showFirstName : TextView
    lateinit var showLastName : TextView
    lateinit var showEmail : TextView
    lateinit var logOutBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
        showFirstName = findViewById(R.id.firstNameText)
        showLastName = findViewById(R.id.lastNameText)
        showEmail = findViewById(R.id.emailAddressText)
        logOutBtn = findViewById(R.id.logOutButton)
        loadProfile()


    }

    private fun loadProfile(){
        val user = auth.currentUser
        val userRefference = databaseReference?.child(user?.uid!!)
        showEmail.text = user?.email
        userRefference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                showFirstName.text = snapshot.child("firstname").value.toString()
                showLastName.text = snapshot.child("lastname").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        logOutBtn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}