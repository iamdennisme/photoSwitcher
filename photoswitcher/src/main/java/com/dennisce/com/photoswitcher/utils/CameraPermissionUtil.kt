package com.dennisce.com.photoswitcher.utils

import android.Manifest
import android.content.Context
import android.hardware.Camera
import android.os.Build
/**
 * Created by dennis on 17/10/2017.
 * so.....
 */
class CameraPermissionUtil {
    // setParameters 是针对魅族MX5 做的。MX5 通过Camera.open() 拿到的Camera
    // 对象不为null

    companion object {
        fun canUse(context: Context): Boolean {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                var canUse = true
                var mCamera: Camera? = null
                try {
                    mCamera = Camera.open()
                    val mParameters = mCamera.parameters
                    mCamera.parameters = mParameters
                } catch (e: Exception) {
                    canUse = false
                }
                if (mCamera != null) {
                    mCamera.release()
                }
                return canUse
            } else {
                return PermissionsUtil.checkPermissions(context, listOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)).isEmpty()
            }
        }
    }
}

