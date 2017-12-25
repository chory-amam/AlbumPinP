package com.example.hakutosuzuki.pinpwithalbum

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView

import java.io.File
import java.io.FileDescriptor
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private var imageView: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById<View>(R.id.image_view) as ImageView
        val button = findViewById<View>(R.id.button2) as Button

        button.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, RESULT_PICK_IMAGE_FILE)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        if (requestCode == RESULT_PICK_IMAGE_FILE && resultCode == Activity.RESULT_OK) {
            var uri: Uri? = null
            if (resultData != null) {
                uri = resultData.data

                Log.d("###", uri!!.toString() + ":")

                try {
                    val bmp = getBitmapFromUri(uri)
                    imageView!!.setImageBitmap(bmp)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    private fun getBitmapFromUri(uri: Uri?): Bitmap {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(uri!!, "r")
        val fileDescriptor = parcelFileDescriptor?.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor?.close()
        return image
    }

    companion object {
        private val RESULT_PICK_IMAGE_FILE = 1000
    }
}
