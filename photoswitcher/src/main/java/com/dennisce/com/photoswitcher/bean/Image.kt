package com.dennisce.com.photoswitcher.bean

import android.text.TextUtils

@Suppress("NAME_SHADOWING")
/**
 * Created by dennis on 17/10/2017.
 * so.....
 */
class Image(var path: String, var name: String, var time: Long) {

    override fun equals(other: Any?): Boolean {
        try {
            val other = other as Image?
            return TextUtils.equals(this.path, other!!.path)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }

        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = path.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + time.hashCode()
        return result
    }
}
