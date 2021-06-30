package com.example.final_exam.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_exam.databinding.LayoutQuizItemBinding
import com.example.final_exam.models.QuizModel

class QuizesAdapter () : RecyclerView.Adapter<QuizesAdapter.QuizesViewHolder>() {
    var clickListener :((QuizModel) -> Unit)? = null
    val quizesList = mutableListOf<QuizModel>()


    fun setOnChosenListener( predicate:((QuizModel) -> Unit)?){
        clickListener = predicate
    }
    fun updateAll(list:List<QuizModel>){
        this.quizesList.clear()
        this.quizesList.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizesViewHolder {
        return QuizesViewHolder(LayoutQuizItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: QuizesViewHolder, position: Int) {
        holder.bind(quizesList[position])
    }

    override fun getItemCount(): Int {
        return quizesList.size
    }

    inner class QuizesViewHolder(private val viewBinding: LayoutQuizItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(questionModel: QuizModel) {
            viewBinding.tvQuizName.text = questionModel.name
            viewBinding.vBg.setOnClickListener {
                clickListener?.invoke(questionModel)
            }
        }
    }
}