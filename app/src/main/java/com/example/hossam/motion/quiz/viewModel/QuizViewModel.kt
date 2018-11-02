package com.example.hossam.motion.quiz.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.MutableLiveData

import android.util.Log
import com.example.agh.hdtask.Data.Network.APIServices
import com.example.hossam.motion.quiz.data.QuizRepository
import com.example.hossam.motion.quiz.Model.Data
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch


public  class   QuizViewModel  : ViewModel() {


    lateinit var quizRepository: QuizRepository




    val errorLD: MutableLiveData<String>? = null
    var  id:Int=0

    fun getResponse(): MutableLiveData<List<Data>> {
        return response
    }

    private var response: MutableLiveData<List<Data>>



    var currentPage: MutableLiveData<Int>


    private var toast: MutableLiveData<String>

    init {
     quizRepository = QuizRepository()

    response = MutableLiveData()
    currentPage = MutableLiveData<Int>()

        toast = MutableLiveData<String>()


    }



    fun sendAnswer( student_id: Int, question_id: Int, answer_id: Int ){

        async {
             val answer_id =  quizRepository.sendAnswer(student_id, question_id, answer_id)
         currentPage.postValue(answer_id)
            Log.e("sendAnswer() response" ,answer_id.toString())

        }



    }

fun getQuestions(){
Log.e("getQuestions","called")
    async {

        Log.e("called","quizRepository.getQuestions()")

      response.postValue(quizRepository.getQuestions())
                   }

}

}