package io.kitchen.easy.easykitchen.kitchen

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import io.kitchen.easy.easykitchen.R

class RateView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    constructor(context: Context?):this(context,null)

    init {
        layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        orientation = HORIZONTAL
    }

    fun setRate(rate:Float){
        val integer = rate.toInt()
        val remainder = rate - integer

        for(i in 0 until integer){
            val fullStar = ImageView(context)
            fullStar.setImageResource(R.drawable.ic_star_black_24dp)
            fullStar.layoutParams = LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1.0f)
            addView(fullStar)
        }

        if(remainder!=0f){
            val fullStar = ImageView(context)
            fullStar.setImageResource(R.drawable.ic_star_half_black_24dp)
            fullStar.layoutParams = LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1.0f)
            addView(fullStar)
        }

        for(i in integer until 4){
            val fullStar = ImageView(context)
            fullStar.setImageResource(R.drawable.ic_star_border_black_24dp)
            fullStar.layoutParams = LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1.0f)
            addView(fullStar)
        }
    }
}