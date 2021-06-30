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
import com.example.final_exam.adapters.AddQuestionsAdapter
import com.example.final_exam.databinding.FragmentCreateQuizBinding
import com.example.final_exam.models.QuestionModel
import com.example.final_exam.models.QuizModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class CreateQuizFragment : Fragment() {
    private var _binding: FragmentCreateQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference


    private var questionsAdapter: AddQuestionsAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCreateQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addQuestion.setOnClickListener {
            AddQuestionDialog.newInstance().show(childFragmentManager, AddQuestionDialog.POSSIBLE_ANSWER_KEY)
        }
        database = Firebase.database.reference

        binding.apply {
            rvQuestions.layoutManager = LinearLayoutManager(requireContext())
            if (questionsAdapter == null) {
                questionsAdapter = AddQuestionsAdapter()
            }
            rvQuestions.adapter = questionsAdapter
            addQuiz.setOnClickListener {
            pushQuestions(QuizModel(questionsAdapter!!.questionList.map {
                it.toMap()
            },etQuizName.text.toString()))
            }
        }
    }
fun pushQuestions(quizModel: QuizModel){
    val key = database.child("testing").push().key ?: return
    val postValues = quizModel.toMap()
    val childUpdates = hashMapOf<String,Any>(
            "/testing/$key" to postValues
    )
    database.updateChildren(childUpdates)

    findNavController().navigate(R.id.action_createQuizFragment_to_exploreQuizesFragment)

}

    fun addItem(questionModel: QuestionModel) {
        questionsAdapter?.insertItem(questionModel)
    }

}
