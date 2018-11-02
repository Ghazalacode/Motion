package com.example.hossam.motion.registration.ViewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.hossam.motion.quiz.data.model.City
import com.example.hossam.motion.registration.Repository.RegistrationRepository
import kotlinx.coroutines.experimental.async


class RegistrationViewModel : ViewModel() {
    internal var registrationRepository: RegistrationRepository

    lateinit var  spinnersData :Array<Array<out Any>>

    val response: MutableLiveData<ArrayList<String>>

    var username = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    private val error = MutableLiveData<String>()
    var age = MutableLiveData<Int>()
    var city = MutableLiveData<Int>()
    var gender = MutableLiveData<Int>()

    val toast = MutableLiveData<String>()

    var studentID = MutableLiveData<Int>()

  var citties = MutableLiveData<List<City>>()


    init {

        registrationRepository = RegistrationRepository()
       email = MutableLiveData()
       username = MutableLiveData()
        response = MutableLiveData()
        studentID = MutableLiveData()
        age = MutableLiveData()
        city = MutableLiveData()
        gender = MutableLiveData()
        citties = MutableLiveData()
    }

    fun registerUser() {
        Log.e("", "registerUser() called with: username = [$username], email = [$email]")

        if (username.value.isNullOrBlank() || email.value.isNullOrBlank()) {
            toast.postValue("تأكد من ملأ جميع الخانات ")
        } else {
            async {
                val data = registrationRepository.registerUser(username.value!!, email.value!!,email.value!!,
                        age.value!! , city.value!!, gender.value!!)

Log.e(" messsadge" , data.toString())

                data.id?.apply { toast.postValue(this.toString())
                                           studentID.postValue(this)}?:Log.e("null"," id is null")
                                    /* ?:data.message?.apply { toast.postValue(this)  }*/


            }
            Log.e("Registration info"  , "age${age.value.toString()} " +
                    "city${city.value.toString()} " +
                    "gender${gender.value.toString()}  ${username.value}   ${email.value}")

        }

    }
    fun getCitties() {

            async {
                val data = registrationRepository.getCitties()
                val city = data.map { it.name  }

                Log.e("Citties"  , "data$data")

               // for (i in 0..data.size) { cittiesNames.add(i , data.get(i).name)  }
                    citties.postValue(data)



            }



    }

}