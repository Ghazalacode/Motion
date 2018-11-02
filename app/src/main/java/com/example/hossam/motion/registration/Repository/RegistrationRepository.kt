package com.example.hossam.motion.registration.Repository

import android.util.Log
import com.example.agh.hdtask.Data.Network.APIServices
import com.example.hossam.motion.quiz.data.model.City
import com.example.hossam.motion.quiz.data.model.Data

import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

public class RegistrationRepository {

    suspend fun <T> Call<T>.await(): T = suspendCancellableCoroutine { cont ->
        cont.invokeOnCompletion { cancel() }
        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>?, t: Throwable) {
                cont.resumeWithException(t)
            }

            override fun onResponse(call: Call<T>?, response: Response<T>) {
                if (response.isSuccessful) {
                    cont.resume(response.body()!!)
                    Log.e("response" , response.body()!!.toString())
                } else {
                    cont.resumeWithException(HttpException(response))
                    Log.e("response Exception" , HttpException(response).localizedMessage)
                    Log.e("response Exception" ,response.errorBody().toString())

                }
            }
        })
    }

    suspend fun registerUser(username: String, email: String,  password: String, age: Int,   city_id: Int, gender: Int): Data
                         =   APIServices.create().register(username,email, password, age, city_id, gender).await().data

    suspend fun getCitties(): List<City>
                         =   APIServices.create().getCitties().await().data




}