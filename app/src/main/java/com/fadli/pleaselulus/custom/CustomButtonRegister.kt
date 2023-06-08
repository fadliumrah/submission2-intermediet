package com.fadli.pleaselulus.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.fadli.pleaselulus.R

class CustomButtonRegister: AppCompatButton {

    private lateinit var backgroundEnable: Drawable
    private lateinit var backgroundDisable: Drawable
    private var txtColor: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttrs: Int) : super(
        context,
        attrs,
        defStyleAttrs
    ) {
        init()
    }

    private fun init(){
        txtColor = ContextCompat.getColor(context, R.color.white)
        backgroundEnable = ContextCompat.getDrawable(context, R.color.secondary_color) as Drawable
        backgroundDisable = ContextCompat.getDrawable(context, R.color.gray_5) as Drawable
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = if (isEnabled) backgroundEnable else backgroundDisable

        textSize = 14f
        setTextColor(txtColor)
        gravity = Gravity.CENTER
        isAllCaps = false
    }

}