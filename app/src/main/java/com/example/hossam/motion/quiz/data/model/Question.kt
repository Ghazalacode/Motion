package com.example.hossam.motion.quiz.data.model

import android.os.Parcelable

import com.example.hossam.motion.quiz.Model.Answer
import kotlinx.android.parcel.Parcelize

@Parcelize data class Question(val question: String, val questionId: Int, val answers: List<Answer>, val number: String, val identifier: String?, val period: Int): Parcelable



data class postAnswerResponse(
    val studentanswer: Studentanswer
)

data class Studentanswer(
    val user_id: String,
    val answer_id: Int,
    val question_id: Int,
    val id: Int
)


data class registerResponse(
    val success: Boolean,
    val data: Data
)

data class Data(
    val id: Int?,
    val token: String,
    val code: Int?,
    val message: String?/*,
    val code: Int,
    val url: String,
    val message: String,
    val errorCount: Int,
    val errors: Errors*/
)


data class Errors(
    val username: Username
)

data class Username(
    val _isUnique: String
)


data class CittiesResponse(
    val data: List<City>
)

data class City(
    val id: Int,
    val name: String,
    val sort: Int,
    val view: String
)