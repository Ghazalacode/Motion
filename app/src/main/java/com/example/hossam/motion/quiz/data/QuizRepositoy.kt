package com.example.hossam.motion.quiz.data

import android.util.Log
import com.example.agh.hdtask.Data.Network.APIServices
import com.example.hossam.motion.quiz.Model.Data
import com.example.hossam.motion.quiz.Model.JsonQuestion
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response


class QuizRepository {


    suspend fun <T> Call<T>.await(): T = suspendCancellableCoroutine { cont ->
        cont.invokeOnCompletion { cancel() }
        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>?, t: Throwable) {
                cont.resumeWithException(t)
            }

            override fun onResponse(call: Call<T>?, response: Response<T>) {
                if (response.isSuccessful) {
                    cont.resume(response.body()!!)
                } else {
                    cont.resumeWithException(HttpException(response))
                    Log.e("response exception" ,HttpException(response).message())
                }
            }
        })
    }

    suspend fun getQuestions(): List<Data> =   APIServices.create().getResults( ).await().data

    suspend fun sendAnswer(student_id: Int, question_id: Int, answer_id: Int): Int
            =   APIServices.create().postAnswer(student_id, question_id, answer_id).await().studentanswer.answer_id








}