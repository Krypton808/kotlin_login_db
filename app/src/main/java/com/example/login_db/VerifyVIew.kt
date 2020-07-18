package com.example.login_db

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class VerifyVIew(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    lateinit var b: Bitmap
    lateinit var drawBitmap: Bitmap
    lateinit var verifyBitmap: Bitmap
    var moveX = 0f
    var moveMax = 0
    var trueX = 0
    var reCalc = true


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (b == null) return

        if (reCalc) {
            var width = width
            var height = height
            var length = width
            drawBitmap = Bitmap.createScaledBitmap(b, width, height, false)

            if (width > height) {
                length = height
            }
            length /= 4

            val x = (0..width - length * 2).random() + length
            val y = (0..height - length * 2).random() + length
            val left = x
            val top = y
            val right = left + length
            val bottom = top + length

            verifyBitmap = Bitmap.createBitmap(drawBitmap, x, y, length, length)

            val moveMax = width - length
            val trueX = x
            reCalc = false
        }
        val paint: Paint? = Paint()
        canvas?.drawBitmap(drawBitmap, 0f, 0f, paint)
        paint?.setColor(Color.parseColor("#ffffffff"))
        canvas?.drawBitmap(verifyBitmap, moveX, y, paint)
    }

    fun setImageBitmap(bitmap: Bitmap) {
        b = bitmap
    }

    fun setMove(precent:Double){
        if(precent<0||precent>1) return;
        moveX = (moveMax*precent).toFloat()
        invalidate()
    }

    fun isTrue(range:Double):Boolean{
        return (moveX>trueX*(1-range) && moveX<trueX*(1+range))
    }

    fun setReDraw(){
        reCalc = true
        invalidate()
    }
}