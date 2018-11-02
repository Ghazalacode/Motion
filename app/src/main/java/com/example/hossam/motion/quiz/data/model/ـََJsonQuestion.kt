package com.example.hossam.motion.quiz.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class JsonQuestion(
    val data: List<Data>
)

data class Data(
        val id: Int,
        val model_id: Int,
        val question: String,
        val question_sort: String?,
        val period: Int,
        val sort: Int,
        val view: String,
        val answers: List<Answer>,
        val model: Model
)

@Parcelize data class Answer(
    val id: Int,
    val question_id: Int,
    val answer: String,
    val photo: String,
    val is_correct: String,
    val sort: Int,
    val view: String
):Parcelable

data class Model(
    val id: Int,
    val name: String,
    val sort: Int
)