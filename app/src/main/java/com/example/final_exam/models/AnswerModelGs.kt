package com.example.final_exam.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnswerModelGs(
        @SerializedName("answer")
        val answer: String? = "",
        @SerializedName("imageUrl")
        val imageUrl: String? = "",
        @SerializedName("correct")
        val correct: Boolean? = null ,
):Parcelable {

}
