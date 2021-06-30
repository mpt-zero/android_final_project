package com.example.final_exam.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_exam.R
import com.example.final_exam.adapters.QuizesAdapter
import com.example.final_exam.databinding.FragmentExploreBinding
import com.example.final_exam.databinding.FragmentProfileBinding
import com.example.final_exam.models.QuizModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ExploreQuizesFragment:Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private var quizesAdapter: QuizesAdapter?  = null
    private lateinit var database: DatabaseReference
    private val quizValuesListener = object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            val quizModelList = snapshot.children.map {
                it.getValue(QuizModel::class.java)!!.copy(id = it.key)
            }
            if (!quizModelList.isNullOrEmpty()){
                quizesAdapter?.updateAll(quizModelList)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("mushni", error.message)
        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentExploreBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (quizesAdapter == null){
            quizesAdapter = QuizesAdapter()
        }
        database = Firebase.database.reference
        val reference = database.child("testing")
        reference.addValueEventListener(quizValuesListener)
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_exploreQuizesFragment_to_createQuizFragment)

        }
        binding.rvQuizes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = quizesAdapter
        }
        quizesAdapter?.setOnChosenListener {
            findNavController().navigate(ExploreQuizesFragmentDirections.actionExploreQuizesFragmentToPlayQuizFragment(it.id?:""))
        }

    }
}