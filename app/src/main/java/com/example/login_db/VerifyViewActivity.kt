package com.example.login_db

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.core.view.drawToBitmap
import kotlinx.android.synthetic.main.activity_verify_view.*

class VerifyViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_view)
        val verifyView1: VerifyVIew = findViewById(R.id.verifyView1)
        verifyView1.setImageBitmap(BitmapFactory.decodeResource(resources,R.drawable.caveira))

        val seekBar: SeekBar = findViewById(R.id.seekBar1)



    }
}
