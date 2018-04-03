package com.dennisce.com.photoswitcher

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.widget.Toast
import com.dennisce.com.photoswitcher.utils.CameraPermissionUtil
import java.util.*


/**
 * Created by dennis on 17/10/2017.
 * so.....
 */
class MultiImageSelector {

    private var mShowCamera = true
    private var mMaxCount = 9
    private var mMode = MultiImageSelectorActivity.MODE_MULTI
    private var mOriginData: ArrayList<String>? = null

    private constructor(context: Context)

    private constructor()

    fun showCamera(show: Boolean): MultiImageSelector? {
        mShowCamera = show
        return sSelector
    }

    fun count(count: Int): MultiImageSelector? {
        mMaxCount = count
        return sSelector
    }

    fun single(): MultiImageSelector? {
        mMode = MultiImageSelectorActivity.MODE_SINGLE
        return sSelector
    }

    fun multi(): MultiImageSelector? {
        mMode = MultiImageSelectorActivity.MODE_MULTI
        return sSelector
    }

    fun origin(images: ArrayList<String>): MultiImageSelector? {
        mOriginData = images
        return sSelector
    }

    fun start(activity: Activity, requestCode: Int) {
        if (CameraPermissionUtil.canUse(activity)) {
            activity.startActivityForResult(createIntent(activity), requestCode)
        } else {
            Toast.makeText(activity, R.string.mis_error_no_permission, Toast.LENGTH_SHORT).show()
        }
    }

    fun start(fragment: Fragment, requestCode: Int) {
        val context = fragment.context
        context?.let {
            if (CameraPermissionUtil.canUse(context)) {
                fragment.startActivityForResult(createIntent(context), requestCode)
            } else {
                Toast.makeText(context, R.string.mis_error_no_permission, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun createIntent(context: Context): Intent {
        val intent = Intent(context, MultiImageSelectorActivity::class.java)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, mShowCamera)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, mMaxCount)
        if (mOriginData != null) {
            intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mOriginData)
        }
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, mMode)
        return intent
    }

    companion object {

        private var sSelector: MultiImageSelector? = null
        fun create(context: Context): MultiImageSelector {
            if (sSelector == null) {
                sSelector = MultiImageSelector(context)
            }
            return sSelector as MultiImageSelector
        }

        fun create(): MultiImageSelector {
            if (sSelector == null) {
                sSelector = MultiImageSelector()
            }
            return sSelector as MultiImageSelector
        }
    }
}
