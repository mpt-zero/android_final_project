package com.example.final_exam.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_exam.adapters.AnswersAdapter
import com.example.final_exam.databinding.FragmentQuestionBinding
import com.example.final_exam.models.QuestionModelGs

class QuestionFragment : Fragment() {
    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!
    private var answersAdapter: AnswersAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val question = requireArguments().getParcelable<QuestionModelGs>(KEY_ANSW)
        val fragPos = requireArguments().getInt(FRAG_POS)
        binding.tvQuestion.text = question?.question
        if (answersAdapter == null) {
            answersAdapter = AnswersAdapter()
        }
        binding.rvAnswers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = answersAdapter
        }
        answersAdapter?.updateAll(question?.answers ?: emptyList())
        binding.btnSubmit.setOnClickListener {
           if (answersAdapter?.currentPosition == answersAdapter?.list?.indexOfFirst {
               it.correct == true
                   }) {
               Toast.makeText(requireContext(),"Correct",Toast.LENGTH_LONG).show()
               Handler(Looper.getMainLooper()).postDelayed({
                   ( requireParentFragment() as PlayQuizFragment).moveToNextFragment(fragPos, true)
               }, 500)
           } else {
               Toast.makeText(requireContext(),"Incorrect", Toast.LENGTH_SHORT).show()
               Handler(Looper.getMainLooper()).postDelayed({
                   ( requireParentFragment() as PlayQuizFragment).moveToNextFragment(fragPos, false)
               }, 500)
           }

        }
    }

    companion object {
        const val KEY_ANSW = "KEY_ANSW"
        const val FRAG_POS = "FRAG_POS"
        fun newInstance(question: QuestionModelGs,fragPos:Int): QuestionFragment {
            return QuestionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_ANSW, question)
                    putInt(FRAG_POS,fragPos)
                }
            }
        }
    }
}