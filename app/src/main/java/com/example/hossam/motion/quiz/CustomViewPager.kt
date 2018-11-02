package com.example.hossam.motion.quiz

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class CustomViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return false
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return false
    }
}