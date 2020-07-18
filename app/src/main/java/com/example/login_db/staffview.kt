package com.example.login_db

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_staffview.*
import android.content.Intent
import kotlinx.android.synthetic.main.activity_login.*
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class staffview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staffview)
        hello.setText("staff UI")
        /*val id = intent.getIntExtra("id")
        val dbHelper = MyDatabaseHelper(this, "UserMsg.db", 1)
        val db = dbHelper.writableDatabase
        val cursor = db.rawQuery("select * from User where id = ?" , arrayOf(id))
        hello.setText("欢迎"+id)*/
    }
}
