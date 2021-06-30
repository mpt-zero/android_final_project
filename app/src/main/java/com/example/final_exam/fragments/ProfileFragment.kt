package com.example.final_exam.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.final_exam.LoginActivity
import com.example.final_exam.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileFragment:Fragment() {
    private var _binding:FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
         loadProfile()
    }
    private fun loadProfile() {
        binding.apply {
            val user = auth.currentUser
            val userRefference = databaseReference?.child(user?.uid!!)

            emailAddressText.text = user?.email
            userRefference?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    firstNameText.text = snapshot.child("firstname").value.toString()
                    lastNameText.text = snapshot.child("lastname").value.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
            logOutButton.setOnClickListener {
                auth.signOut()
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
        }
    }
}