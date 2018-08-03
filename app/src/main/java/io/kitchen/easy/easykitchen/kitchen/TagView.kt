package io.kitchen.easy.easykitchen.kitchen

import android.content.Context
import android.graphics.Color
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.AttributeSet
import android.widget.TextView
import io.kitchen.easy.easykitchen.R

class TagView(context: Context?, attrs: AttributeSet?) : TextView(context, attrs) {
    constructor(context: Context?) : this(context, null)

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        val drawable = resources.getDrawable(R.drawable.tag_background, null)
        val color = colors[Math.abs(text.toString().hashCode()) % colors.size]
        val wrappedDrawable = DrawableCompat.wrap(drawable)
        wrappedDrawable.setTint(ResourcesCompat.getColor(resources, color, null))
        background = wrappedDrawable
        setTextColor(Color.WHITE)
        setTextSize(12f)
        setPadding(25, 2, 25, 2)
    }

    companion object {
        val colors = arrayOf(
                R.color.tag1
                , R.color.tag2
                , R.color.tag3
                , R.color.tag5
                , R.color.tag6
                , R.color.tag7
                , R.color.tag8
                , R.color.tag9
                , R.color.tag10
                , R.color.tag11
                , R.color.tag12
                , R.color.tag13
                , R.color.tag14
                , R.color.tag15
                , R.color.tag16
                , R.color.tag17
                , R.color.tag18
                , R.color.tag19
                , R.color.tag20
                , R.color.tag21
                , R.color.tag22
        )
    }
}