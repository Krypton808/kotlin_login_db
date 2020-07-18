package com.example.login_db
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream
import kotlin.random.Random
import android.R.id
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.Image



class PhotoManagement(){
    //从资源中获取Bitmap对象
    /**
    val res: Resources = resources
    val bmp:Bitmap = BitmapFactory.decodeResource(res,Random_getid(context))
    **/


    //随机产生imgID，模拟user选择头像
    fun Random_getid(context:Context):Int{
        val ar:TypedArray = context.resources.obtainTypedArray(R.array.arr_images)
        val i = (0..4).random()
        val resid = ar.getResourceId(i,0)
        return resid
    }

    //把图片转换成字节
    fun img(bm: Bitmap):ByteArray{
        val baos:ByteArrayOutputStream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
        return baos.toByteArray()
    }


    //获取存入数据库的图片（Bitmap）
    //ibv image binary values
    fun getBmp(location:Int):Bitmap{
        val context:Context = WdTools.getContext()
        val dbHelper = MyDatabaseHelper(context, "Login.db", 1)
        val db = dbHelper.writableDatabase
        val cursor = db.rawQuery("select* from empImage",null)
        cursor.moveToPosition(location)
        val ibv:ByteArray = cursor.getBlob(cursor.getColumnIndex("IMAGE"))
        cursor.close()
        val bmpout:Bitmap = BitmapFactory.decodeByteArray(ibv,0,ibv.size)
        return bmpout
    }

    //转换获取的图片（Bitmap）为Drawable
    fun change_to_drawable(bp:Bitmap):Drawable{
        val context:Context = WdTools.getContext()
        val resourses:Resources = context.resources
        val bm:Bitmap = bp
        val bd:BitmapDrawable = BitmapDrawable(resourses,bm)
        return bd
    }
}