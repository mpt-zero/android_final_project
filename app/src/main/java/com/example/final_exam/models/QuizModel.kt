package com.example.final_exam.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class QuizModel(
        val questions: List<Map<String,Any?>>? = null ,
        val name:String? = "",
        val id:String? = ""
) {
    fun toMap():Map<String,Any?>{
        return mapOf(
                "questions" to questions,
                "name" to name,
        )
    }
}
