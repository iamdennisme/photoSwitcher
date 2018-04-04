package com.dennisce.com.photoswitcher.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import java.util.*

/**
 * Created by dennis on 2017/8/4.
 * introduction:permissionsUtil
 *
 */

 object PermissionsUtil {

    /*
     * check permissions
     * Returns a List of unauthorized
     * */
    fun checkPermissions(context: Context, permissions: List<String>): List<String> {
        return permissions.filterNotTo(ArrayList()) { checkPermission(context, it) }
    }

    /*
     * check single permission
     * */
    fun checkPermission(context: Context, permissions: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(context,permissions) == PackageManager.PERMISSION_GRANTED
        } else {
            //if sdk <23,always return true,Do not carry out dynamic authorization
            true
        }
    }

    fun requestPermissions(activity: Activity, permissions: List<String>, requestCode: Int) {

        val permissionsArr = permissions.toTypedArray()
        request(activity, permissionsArr, requestCode)
    }

    /*
    * request permission
    * */

    fun requsetPermission(activity: Activity, permission: String, requestCode: Int) {
        val permissionsArr = arrayOf(permission)
        request(activity, permissionsArr, requestCode)
    }

    private fun request(activity: Activity, permissions: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    /*
    * check permissions
    * Returns a List of unauthorized
    * */
    fun checkPermissionsRequest(permissions: Array<out String>, grantResult: IntArray): List<String> {
        return permissions.indices
                .filter { grantResult[it] != PackageManager.PERMISSION_GRANTED }
                .map { permissions[it] }
    }
}
