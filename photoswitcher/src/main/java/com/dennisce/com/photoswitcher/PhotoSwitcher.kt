package com.dennisce.com.photoswitcher

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.dennisce.com.photoswitcher.Listener.PhotoResultListener
import java.io.File
import java.io.FileInputStream
import java.util.*


/**
 * Created by ${dennis} on 7/26/16.
 */
class PhotoSwitcher private constructor(private val mActivity: Activity) {
    private var selector: MultiImageSelector? = null

    init {
        init()
    }

    fun setCount(count: Int): PhotoSwitcher {
        selector!!.count(count)
        return this
    }

    private fun init() {
        selector = MultiImageSelector.create(mActivity)
                .showCamera(true)?.count(9)?.multi()
    }

    fun getPhoto(RequestCode: Int) {
        selector!!.start(mActivity, RequestCode)
    }

    fun patchsToBitmaps(patches: List<String>): List<Bitmap> {
        val bitmaps = ArrayList<Bitmap>()
        for (patch in patches) {
            decodeFile(patch)?.let { bitmaps.add(it) }
        }
        return bitmaps
    }

    companion object {
        fun dealWith(requestCode: Int, resultCode: Int, data: Intent?, yourCode: Int, resultListener: PhotoResultListener) {
            if (requestCode != yourCode || resultCode != Activity.RESULT_OK || data == null) {
                resultListener.failure()
                return
            }

            val path: List<String> = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT)
            resultListener.result(path)
        }

        fun getInstance(activity: Activity): PhotoSwitcher {
            return PhotoSwitcher(activity)
        }

        fun decodeFile(path: String): Bitmap? {
            var b: Bitmap? = null
            val f = File(path)
            // Decode image size
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true

            var fis: FileInputStream? = null
            try {
                fis = FileInputStream(f)
                BitmapFactory.decodeStream(fis, null, o)
                fis.close()

                val MAX_SIZE = 1024 // maximum dimension limit
                var scale = 1
                if (o.outHeight > MAX_SIZE || o.outWidth > MAX_SIZE) {
                    scale = Math.pow(2.0, Math.round(Math.log(MAX_SIZE / Math.max(o.outHeight, o.outWidth).toDouble()) / Math.log(0.5)).toInt().toDouble()).toInt()
                }

                // Decode with inSampleSize
                val o2 = BitmapFactory.Options()
                o2.inSampleSize = scale

                fis = FileInputStream(f)
                b = BitmapFactory.decodeStream(fis, null, o2)
                fis.close()

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return b
        }
    }
}

