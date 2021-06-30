package com.example.final_exam.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize


@IgnoreExtraProperties
@Parcelize
data class PossibleAnswerModel(
        val answer: String? = "",
        val imageUrl: String? = "",
        val correct: Boolean? = false
):Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "answer" to answer,
                "imageUrl" to imageUrl,
                "correct" to correct)
    }
}
