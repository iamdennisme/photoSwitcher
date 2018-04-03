package com.dennisce.com.photoswitcher.Listener

/**
 * author:dennis
 * date:03/04/2018 14:21
 */

interface PhotoResultListener {
    fun result(result: List<String>)
    fun failure()
}