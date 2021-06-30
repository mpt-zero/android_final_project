package com.example.final_exam.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.parcelize.WriteWith

@IgnoreExtraProperties
data class QuestionModel(
        val question: String? = "",
        val answers: List<Map<String, Any?>>? = null,
)
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf("question" to question,
                "answers" to answers)
    }
}
