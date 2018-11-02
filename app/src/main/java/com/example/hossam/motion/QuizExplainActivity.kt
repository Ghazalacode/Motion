package com.example.hossam.motion

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.hossam.motion.registration.View.RegistrationActivity
import kotlinx.android.synthetic.main.activity_quiz_explain.*

import org.jetbrains.anko.sdk27.coroutines.onClick
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class QuizExplainActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_quiz_explain)

        btnGoRegister.setOnClickListener {    startActivity(Intent(this@QuizExplainActivity ,RegistrationActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)) }



    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
