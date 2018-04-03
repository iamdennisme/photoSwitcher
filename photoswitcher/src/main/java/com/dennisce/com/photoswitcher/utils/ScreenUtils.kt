package com.dennisce.com.photoswitcher.utils

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

/**
 * Created by dennis on 17/10/2017.
 * so.....
 */
object ScreenUtils {

    fun getScreenSize(context: Context): Point {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val out = Point()
        display?.getSize(out)
        return out
    }
}
