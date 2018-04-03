package com.dennisce.com.photoswitcher.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * Created by dennis on 17/10/2017.
 * so.....
 */
class SquareFrameLayout : FrameLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredWidth)
    }
}
