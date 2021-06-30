package com.example.final_exam.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.final_exam.R
import com.example.final_exam.databinding.FragmentPlayQuizBinding
import com.example.final_exam.models.PossibleAnswerModel
import com.example.final_exam.models.QuestionModel
import com.example.final_exam.models.QuizModel
import com.example.final_exam.models.QuizModelGS
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import java.lang.Exception

class PlayQuizFragment : Fragment() {
    private var _binding: FragmentPlayQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    private lateinit var id: String
    private val fragmentList = mutableListOf<Fragment>()
    private var correctAnserCounter: Int = 0
    private val quizValuesListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            snapshot.getValue(QuizModelGS::class.java)?.apply {
                if (name!= binding.quizNAme.text){
                    binding.quizNAme.text = name
                }
                fragmentList.addAll(questions!!.mapIndexed { index, questionModelGs ->
                    QuestionFragment.newInstance(questionModelGs,index)

                } )
            }
            binding.questionCount.text = getString(R.string.question_count_number,fragmentList.size.toString())
            startTransactions(fragmentList.first())

        }

        override fun onCancelled(error: DatabaseError) {
        }

    }

    private fun startTransactions(fragment: Fragment) {
        if (childFragmentManager.findFragmentById(R.id.fragment) != fragment) {
            childFragmentManager.beginTransaction().replace(R.id.fragment, fragment).commit()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPlayQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: PlayQuizFragmentArgs by navArgs()
        id = args.id
        database = Firebase.database.reference
        val reference = database.child("testing/${id}")
        reference.addValueEventListener(quizValuesListener)

    }

    fun moveToNextFragment(i: Int, wasCorrect: Boolean) {
        if (wasCorrect) {
            correctAnserCounter++
        }
        if (i +1  >=fragmentList.size ){
            findNavController().navigate(PlayQuizFragmentDirections.actionPlayQuizFragmentToExploreQuizesFragment())
            Toast.makeText(requireContext(),"Quiz finished!! Correct Answers ${correctAnserCounter}/${fragmentList.size}", Toast.LENGTH_LONG).show()

        } else {
            try {
                startTransactions(fragmentList[i + 1 ])
            } catch (e:Exception){
            }
        }

    }
}