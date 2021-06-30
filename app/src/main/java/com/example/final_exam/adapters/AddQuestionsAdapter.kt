package com.example.final_exam.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_exam.databinding.LayoutQuestionItemBinding
import com.example.final_exam.models.QuestionModel

class AddQuestionsAdapter : RecyclerView.Adapter<AddQuestionsAdapter.QuestionViewHolder>() {

     val questionList = mutableListOf<QuestionModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(LayoutQuestionItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
       holder.bind(questionList[position])
    }

    override fun getItemCount(): Int {
        return questionList.size
    }
    fun insertItem(questionModel: QuestionModel){
        questionList.add(questionModel)
        notifyDataSetChanged()
    }
    inner class QuestionViewHolder(private val viewBinding: LayoutQuestionItemBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(questionModel:QuestionModel){
            viewBinding.tvQuestion.text = questionModel.question
        }
    }
}