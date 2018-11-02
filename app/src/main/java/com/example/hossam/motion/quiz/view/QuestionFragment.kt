package com.example.hossam.motion.quiz.view

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import com.example.hossam.motion.FinishingActivity
import com.example.hossam.motion.quiz.adapters.RadioAdapter
import com.example.hossam.motion.quiz.data.model.Question
import com.example.hossam.motion.quiz.Model.RecAnswer
import com.example.hossam.motion.quiz.viewModel.QuizViewModel
import com.example.hossam.motion.R
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.fragment_question.view.*
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.toast
import java.io.IOException
import java.lang.Boolean.getBoolean
import java.net.*
import java.util.concurrent.TimeUnit

class QuestionFragment : Fragment() {
    private lateinit var mLifecycleRegistry: LifecycleRegistry

    private lateinit var viewModel: QuizViewModel
    private lateinit var mAdapter: RadioAdapter

    var timer: CountDownTimer?=null

    // create boolean for starting
    private var isViewShown = false


     private lateinit var mSocket: Socket

    lateinit var   onNewMessage : Emitter.Listener





    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Fragment1.
     */
    fun newInstance(question: Question, isLast: Boolean): QuestionFragment {
        //cannot access when in companion object
        val myFragment = QuestionFragment()

        val args = Bundle()

        args.putBoolean("isLast", isLast)
      args.putParcelable("question" , question)

        Log.e("list", question.answers.toString())
        Log.e("array", question.answers.toTypedArray().toString())
        timer=null
        myFragment.arguments = args

        return myFragment
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(QuizViewModel::class.java)
        } ?: throw Exception("Invalid Activity")


        mLifecycleRegistry = LifecycleRegistry(this)
        mLifecycleRegistry.markState(Lifecycle.State.CREATED)

        try {

            mSocket = IO.socket("http://192.168.1.2:3000")

        } catch (e:URISyntaxException){   Log.e("socket exception" , e.stackTrace.toString())        }

        val onConnect = Emitter.Listener {runOnUiThread { toast("      CONNECTED  ")  }  }
        with(mSocket){

            on(Socket.EVENT_CONNECT ,  onConnect   )

            connect()

        }

    }



    /*  fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                       savedInstanceState: Bundle): View {
          // Inflate the layout for this fragment
          val view = inflater.inflate(R.layout.fragment_question, container, false)
          val imageView = view.findViewById(R.id.imageView)
          imageView.setImageResource(imageResId)

          return view
      }*/

    private var isLast: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        arguments?.getParcelable<Question>("question")?.apply {
            isLast = arguments!!.getBoolean("isLast")
            if (isLast) {

                startActivity(Intent(this@QuestionFragment.activity ,FinishingActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))

            }
            val answers = this.answers
            Log.e("fromBundle", this.answers.toString())
            view.question.text = this.question
            view.identifier.text = this.identifier


            val periodMilli  = TimeUnit.MINUTES.toMillis(this.period.toLong())
            Log.e("periodMilli", "periodMilli $periodMilli")
              if (timer!=null) timer=null
             timer = object : CountDownTimer(periodMilli+1000 , 1000) {

                override fun onTick(secondsUntilDone: Long) {

                   view.number.text = TimeUnit.MILLISECONDS.toSeconds(secondsUntilDone).toString()

                }

                override fun onFinish() {
                    toast("انتهى وقت السؤال")
                }
            }

            if (view != null) {
                isViewShown = true

                timer?.start()

            }

            val appContext = this@QuestionFragment.activity!!.applicationContext
            val answersList = listOf(RecAnswer(answers.get(0).answer, RadioButton(appContext))
                    , RecAnswer(answers.get(1).answer, RadioButton(appContext))
                    , RecAnswer(answers.get(2).answer, RadioButton(appContext))
                    , RecAnswer(answers.get(3).answer, RadioButton(appContext)))
            mAdapter = RadioAdapter(appContext, answersList)
            mAdapter.notifyDataSetChanged()

            with(view.recyclerAnswers) {
                layoutManager = LinearLayoutManager(appContext)
                itemAnimator = DefaultItemAnimator()
                adapter = mAdapter

            }

            view.btnSubmitAnswer.setOnClickListener {
                val intAdapter = mAdapter.mSelectedItem
                if (intAdapter==-1) {viewModel.sendAnswer(viewModel.id
                        ,this.questionId,0)
                    Log.e("viewModel.sendAnswer" , "id =${viewModel.id} questionId =${this.questionId} answer_id=0  ")}
                else
                {viewModel.sendAnswer(viewModel.id
                       ,this.questionId,answers.get(intAdapter).id)
                    Log.e("viewModel.sendAnswer" , "id =${viewModel.id} questionId =${this.questionId} position=${answers.get(intAdapter).id}  ")
                }



            }

            var onNewMessage = Emitter.Listener(){
            runOnUiThread { if(isAdded()){

                Log.e("recieved value" , it[0].toString())
                val intAdapter = mAdapter.mSelectedItem
                if (intAdapter==-1) {viewModel.sendAnswer(viewModel.id
                        ,this.questionId,0)}
                else
                { viewModel.sendAnswer(viewModel.id
                            ,this.questionId,answers.get(intAdapter).id)}
            }
              }   }

            mSocket.on("next", onNewMessage)

        } ?: IllegalArgumentException("QuestionFragment arguments is missing")

        return view
    }

    override fun onPause() {
        super.onPause()
        Log.e("onPause()" , " onPause()  called ")

        timer?.cancel()

    }

    override fun onDestroy() {
        try {
            this.mSocket.disconnect()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        super.onDestroy()
        timer?.cancel()


    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

           if (isVisibleToUser) timer?.start()

    }
}
