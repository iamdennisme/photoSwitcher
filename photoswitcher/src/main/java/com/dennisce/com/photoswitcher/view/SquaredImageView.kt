package com.dennisce.com.photoswitcher.view

import android.content.Context
import android.util.AttributeSet

/**
 * Created by dennis on 17/10/2017.
 * so.....
 */
/** An image view which always remains square with respect to its width.  */
internal class SquaredImageView : android.support.v7.widget.AppCompatImageView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredWidth)
    }
}
