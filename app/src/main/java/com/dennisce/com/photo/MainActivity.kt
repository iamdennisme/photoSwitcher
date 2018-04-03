package com.dennisce.com.photo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dennisce.com.photoswitcher.Listener.PhotoResultListener
import com.dennisce.com.photoswitcher.PhotoSwitcher

class MainActivity : AppCompatActivity() {
    private val CODE = 111
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //request permissions
        PhotoSwitcher.getInstance(this).setCount(9).getPhoto(CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        PhotoSwitcher.dealWith(requestCode, resultCode, data, CODE, object : PhotoResultListener {
            override fun failure() {

            }

            override fun result(result: List<String>) {
                result.map {
                    Log.d("result", it)

                }
            }
        })
    }
}
