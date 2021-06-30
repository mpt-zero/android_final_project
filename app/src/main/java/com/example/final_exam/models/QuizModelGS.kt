package com.example.final_exam.models

import com.google.gson.annotations.SerializedName

data class QuizModelGS(
        @SerializedName("questions")
        val questions:List<QuestionModelGs>? = null ,
        @SerializedName("name")
        val name:String?  = null
)
