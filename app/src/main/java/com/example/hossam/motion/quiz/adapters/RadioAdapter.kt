package com.example.hossam.motion.quiz.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import com.example.hossam.motion.quiz.Model.RecAnswer
import com.example.hossam.motion.R


/**
 * Created by agh on 04/02/17.
 */
class RadioAdapter(private val mContext: Context, var mItems: List<RecAnswer>) : RecyclerView.Adapter<RadioAdapter.ViewHolder>() {

    var mSelectedItem = -1


    override fun getItemCount(): Int {
        return mItems.size
    }

    init {


    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.mRadio.isChecked = position == mSelectedItem

        viewHolder.mText.setText(mItems[position].answer)
        when(position){
            0 ->  viewHolder.mText.setBackgroundResource(R.drawable.bg_lightgrey)
            1 ->  viewHolder.mText.setBackgroundResource(R.drawable.bg_darkgrey)
            2 ->  viewHolder.mText.setBackgroundResource(R.drawable.bg_blue)
            3 ->  viewHolder.mText.setBackgroundResource(R.drawable.bg_orange)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int):ViewHolder{
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.answer_row, viewGroup, false)
        return ViewHolder(view)
    }


   inner class ViewHolder(inflate: View) : RecyclerView.ViewHolder(inflate) {

        var mRadio: RadioButton
        var mText: TextView

        init {
            mText = inflate.findViewById(R.id.tvanswer) as TextView

            mRadio = inflate.findViewById(R.id.radioButton) as RadioButton
            val clickListener = View.OnClickListener {
                mSelectedItem = adapterPosition
                notifyDataSetChanged()
            }
            itemView.setOnClickListener(clickListener)
            mRadio.setOnClickListener(clickListener)
        }
    }
}
