package com.example.final_exam.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuestionModelGs (
    @SerializedName("question")
    val question: String = "",
    @SerializedName("answers")
    val answers: List<AnswerModelGs>? = null
):Parcelable