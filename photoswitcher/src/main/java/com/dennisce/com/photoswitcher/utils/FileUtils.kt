package com.dennisce.com.photoswitcher.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.os.Environment.MEDIA_MOUNTED
import android.text.TextUtils
import java.io.File
import java.io.IOException

/**
 * Created by dennis on 17/10/2017.
 * so.....
 */
object FileUtils {

    private val JPEG_FILE_PREFIX = "IMG_"
    private val JPEG_FILE_SUFFIX = ".jpg"


    private val EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE"

    @Throws(IOException::class)
    fun createTmpFile(context: Context): File {
        var dir: File?
        if (TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
            dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            if (!dir!!.exists()) {
                dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera")
                if (!dir!!.exists()) {
                    dir = getCacheDirectory(context, true)
                }
            }
        } else {
            dir = getCacheDirectory(context, true)
        }
        return File.createTempFile(JPEG_FILE_PREFIX, JPEG_FILE_SUFFIX, dir)
    }

    @SuppressLint("SdCardPath")
    private fun getCacheDirectory(context: Context, preferExternal: Boolean = true): File {
        var appCacheDir: File? = null
        val externalStorageState: String = try {
            Environment.getExternalStorageState()
        } catch (e: NullPointerException) { // (sh)it happens (Issue #660)
            ""
        } catch (e: IncompatibleClassChangeError) { // (sh)it happens too (Issue #989)
            ""
        }

        if (preferExternal && MEDIA_MOUNTED == externalStorageState && hasExternalStoragePermission(context)) {
            appCacheDir = getExternalCacheDir(context)
        }
        if (appCacheDir == null) {
            appCacheDir = context.cacheDir
        }
        if (appCacheDir == null) {
            val cacheDirPath = "/data/data/" + context.packageName + "/cache/"
            appCacheDir = File(cacheDirPath)
        }
        return appCacheDir
    }

    private fun getExternalCacheDir(context: Context): File? {
        val dataDir = File(File(Environment.getExternalStorageDirectory(), "Android"), "data")
        val appCacheDir = File(File(dataDir, context.packageName), "cache")
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                return null
            }
            try {
                File(appCacheDir, ".nomedia").createNewFile()
            } catch (e: IOException) {
            }

        }
        return appCacheDir
    }

    private fun hasExternalStoragePermission(context: Context): Boolean {
        val perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION)
        return perm == PackageManager.PERMISSION_GRANTED
    }

}
