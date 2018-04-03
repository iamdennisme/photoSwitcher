package com.dennisce.com.photoswitcher.bean

import android.text.TextUtils

@Suppress("NAME_SHADOWING")
/**
 * Created by dennis on 17/10/2017.
 * so.....
 */
class Folder {
    var name: String? = null
    var path: String? = null
    var cover: Image? = null
    var images: List<Image>? = null

    override fun equals(other: Any?): Boolean {
        try {
            val other = other as Folder?
            return TextUtils.equals(other!!.path, path)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }

        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (path?.hashCode() ?: 0)
        result = 31 * result + (cover?.hashCode() ?: 0)
        result = 31 * result + (images?.hashCode() ?: 0)
        return result
    }
}
