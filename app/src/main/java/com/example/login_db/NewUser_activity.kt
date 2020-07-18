package com.example.login_db

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.accountEdit
import kotlinx.android.synthetic.main.activity_login.login
import kotlinx.android.synthetic.main.activity_login.passwordEdit
import kotlinx.android.synthetic.main.activity_new_user_activity.*
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.method.TextKeyListener.clear
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.login_db.RSACrypt.getPrivateKey
import java.lang.Exception
import java.lang.NullPointerException



class NewUser_activity : AppCompatActivity(), View.OnClickListener {


    var emp_position = "staff"
    override fun onClick(v: View?) {
        when (v?.id) {
            staff.id -> emp_position = "staff"
            manager.id -> emp_position = "manager"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user_activity)
        staff.setOnClickListener(this)
        manager.setOnClickListener(this)

        val dbHelper = MyDatabaseHelper(this, "Login.db", 1)
        login.setOnClickListener {

            val account = accountEdit.text.toString()
            val password = passwordEdit.text.toString()
            val encrypt = RSACrypt.encryptByPrivateKey(password, getPrivateKey())
            val emp_name = nameEdit.text.toString()
            val phone = phone_Edit.text.toString()
            val department_id = dep_idEdit.text.toString()



            Toast.makeText(this, "aaaa" + emp_name, Toast.LENGTH_SHORT).show()
            val db = dbHelper.writableDatabase

            if (phone.length != 11 || account.length >= 10) {
                AlertDialog.Builder(this).apply {
                    setTitle("输入错误")
                    setMessage("请重新输入")
                    setCancelable(false)
                    setPositiveButton("Yes") { _, _ ->
                        finish()
                        val intent = Intent(context, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    show()
                }
            } else {

                db.execSQL(
                    "insert into empinfo(account,password,emp_name,emp_position,department_id,phone)values(?,?,?,?,?,?)",
                    arrayOf(account, encrypt, emp_name, emp_position, department_id, phone)
                )


                val PM: PhotoManagement = PhotoManagement()
                val res: Resources = resources
                val bmp: Bitmap = BitmapFactory.decodeResource(res, PM.Random_getid(this))
                val ibv: ByteArray = PM.img(bmp)

                db.execSQL(
                    "insert into empImage(IMAGE)values(?)", arrayOf(ibv)
                )


                AlertDialog.Builder(this).apply {
                    setTitle("注册成功")
                    setMessage("是否登录？")
                    setCancelable(false)
                    setPositiveButton("Yes") { _, _ ->
                        finish()
                        val cursor = db.rawQuery("select emp_id from empinfo", null)
                        cursor.moveToLast()
                        val ID = cursor.getInt(cursor.getColumnIndex("emp_id"))
                        cursor.close()

                        db.execSQL(
                            "insert into log(behavior,object,time,date) values('login',?,time('now'),date('now'))",
                            arrayOf(ID)
                        )
                        if (emp_position == "staff") {

                            val intent = Intent(context, staffview::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(context, managerview::class.java)
                            startActivity(intent)
                        }
                    }
                    setNegativeButton("No") { _, _ ->
                        finish()
                    }
                    show()
                }
            }
        }
    }
}