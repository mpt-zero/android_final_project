package com.example.final_exam.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.final_exam.databinding.LayoutAnswerItemBinding
import com.example.final_exam.databinding.LayoutQuizItemBinding
import com.example.final_exam.models.AnswerModelGs
import com.example.final_exam.models.PossibleAnswerModel
import com.example.final_exam.models.QuizModel
import com.squareup.picasso.Picasso

class AnswersAdapter: RecyclerView.Adapter<AnswersAdapter.AnswersViewHolder>() {
     val list = mutableListOf<AnswerModelGs>()
    var lastCheckedPosition = -1
    var currentPosition = -1
    inner class AnswersViewHolder(private val viewBinding: LayoutAnswerItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(answer: AnswerModelGs, position: Int) {
            if (!answer.imageUrl.isNullOrEmpty()){
                Picasso.get().load(answer.imageUrl).into(viewBinding.ivImage)
            } else {
                viewBinding.ivImage.isVisible = false
            }
            viewBinding.radioButton.isChecked = position == lastCheckedPosition
            viewBinding.radioButton.setOnClickListener {
                val copyLstPos = lastCheckedPosition
                lastCheckedPosition = adapterPosition
                notifyItemChanged(copyLstPos)
                notifyItemChanged(lastCheckedPosition)
                currentPosition = adapterPosition

            }
            viewBinding.tvAnswer.text = answer.answer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswersViewHolder {
        return AnswersViewHolder(LayoutAnswerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: AnswersViewHolder, position: Int) {
        holder.bind(list[position],position)
    }
    fun updateAll(list:List<AnswerModelGs>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}