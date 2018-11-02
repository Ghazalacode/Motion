package com.example.hossam.motion.quiz.view

import android.arch.lifecycle.*
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.hossam.motion.LocaleUtils
import com.example.hossam.motion.quiz.adapters.QuestionsPagerAdapter
import com.example.hossam.motion.quiz.data.model.Question
import com.example.hossam.motion.quiz.Model.Data
import com.example.hossam.motion.quiz.viewModel.QuizViewModel
import com.example.hossam.motion.R
import com.example.hossam.motion.quiz.Model.Answer
import kotlinx.android.synthetic.main.activity_quiz.*
import org.jetbrains.anko.toast
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class QuizActivity : AppCompatActivity() ,LifecycleOwner {

    private lateinit var mLifecycleRegistry: LifecycleRegistry
    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }
    lateinit var  apiQuestions:List<Data>
    private lateinit var pagerAdapter: QuestionsPagerAdapter
    lateinit var viewModel : QuizViewModel
    init {
        LocaleUtils.updateConfig(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("JF-Flat-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
        setContentView(R.layout.activity_quiz)
        viewModel =  ViewModelProviders.of(this).get(QuizViewModel::class.java)
        mLifecycleRegistry =  LifecycleRegistry(this)
        mLifecycleRegistry.markState(Lifecycle.State.CREATED)

        intent.extras?.apply{ viewModel.id = getInt("studentID")   }?:Log.e("studentID" ,"null null null")

      viewModel.getQuestions()
        viewModel.getResponse().observe(this,
                Observer {   apiQuestions =  it!!
                Log.e("it" , it.toString())
                    val questions = arrayListOf<Question>(

                    )

                    for (i in 0..apiQuestions.size-1) {
                        val questiontext =   apiQuestions.get(i).question
                        val questionID =   apiQuestions.get(i).id
                        val answers =  apiQuestions.get(i).answers
                        val period =  apiQuestions.get(i).period

                        val number =   apiQuestions.get(i).id
                        val identifier =   apiQuestions.get(i).question_sort?.toString()


                        questions.add(Question(questiontext ,questionID, answers ,number.toString() ,identifier ,period) )
                    }


                    pagerAdapter = QuestionsPagerAdapter(supportFragmentManager, questions)
                    viewPager.adapter = pagerAdapter

                })

        viewModel.currentPage.observe(this , Observer {
            Log.e("it ",it.toString())
            if (it==-1) toast("رجاء اختر اجابة أولا")
            else { toast(it!!.toString())

            viewPager.currentItem = (viewPager.currentItem +1)}
        })
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
