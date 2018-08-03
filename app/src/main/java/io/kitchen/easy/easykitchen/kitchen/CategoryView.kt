package io.kitchen.easy.easykitchen.kitchen

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide

class CategoryView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    constructor(context: Context?) : this(context, null)

    private var select: Boolean = false
    private var doOnSelected: ((selected: Boolean, title: String) -> Unit)? = null

    var image: ImageView
    var title: TextView

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        image = ImageView(context)
        image.alpha = 0.3f

        image.layoutParams = ViewGroup.LayoutParams(180, 180)
        title = TextView(context)
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        title.setPadding(0, 40, 0, 0)
        title.gravity = Gravity.CENTER

        setOnClickListener {
            if (select) {
                image.alpha = 0.3f
            } else {
                image.alpha = 1f
            }

            select = !select

            doOnSelected?.invoke(select, title.text.toString())
        }
        addView(image)
        addView(title)
    }

    fun setup(title: String, image: Int, doOnSelected: (selected: Boolean, title: String) -> Unit) {
        this.title.setText(title)
        Glide.with(this).load(image).into(this.image)

        this.doOnSelected = doOnSelected
    }

}