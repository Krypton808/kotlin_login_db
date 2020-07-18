package com.example.login_db

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.activity_login.*
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.preference.Preference
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.login_db.RSACrypt.getPublicKey
import com.mob.MobSDK
import kotlinx.android.synthetic.main.activity_login.accountEdit
import kotlinx.android.synthetic.main.activity_login.login
import kotlinx.android.synthetic.main.activity_login.passwordEdit
import kotlinx.android.synthetic.main.activity_new_user_activity.*
import java.lang.Exception
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import java.util.*
import kotlin.collections.HashMap


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val prefs = getPreferences(Context.MODE_PRIVATE)
        val isRemember = prefs.getBoolean("remember_password",false)
        if(isRemember){
            val account = prefs.getString("account","")
            val password = prefs.getString("password","")
            accountEdit.setText(account)
            passwordEdit.setText(password)
            rememberPass.isChecked = true
        }

        val dbHelper = MyDatabaseHelper(this, "Login.db", 1)
        var flag = false
        //将验证码用图片形式显示
        showVerify.setImageBitmap(CreateVCodeUtils.getInstance().createBitmap())
        var realCode = CreateVCodeUtils.getInstance().getCode()?.toLowerCase()
        Toast.makeText(this, realCode, Toast.LENGTH_LONG)

        showVerify.setOnClickListener {
            showVerify.setImageBitmap(CreateVCodeUtils.getInstance().createBitmap())
            realCode = CreateVCodeUtils.getInstance().getCode()?.toLowerCase()
            Toast.makeText(this, realCode, Toast.LENGTH_LONG)
        }

        login.setOnClickListener {
            val verifytext = verifyEidt.text.toString().toLowerCase()
            if (verifytext.equals(realCode)) {
                flag = true
                Toast.makeText(this, "验证码正确", Toast.LENGTH_SHORT)
                showVerify.setImageBitmap(CreateVCodeUtils.getInstance().createBitmap())
                realCode = CreateVCodeUtils.getInstance().getCode()?.toLowerCase()
            } else {
                flag = false
                AlertDialog.Builder(this).apply {
                    setTitle("验证码错误")
                    setMessage("请重新输入")
                    setCancelable(false)
                    setPositiveButton("Yes") { _, _ ->
                        finish()
                        val intent = Intent(context, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    show()
                }

            }

            try {
                val account = accountEdit.text.toString()
                val password = passwordEdit.text.toString()


                Toast.makeText(this, account, Toast.LENGTH_SHORT).show()
                val db = dbHelper.writableDatabase

                val cursor =
                    db.rawQuery("select* from v_empinfo3 where account = ?", arrayOf(account))
                cursor.moveToFirst()
                val psw = cursor.getString(cursor.getColumnIndex("password"))
                val decryptByPublicKey = RSACrypt.decryptByPublicKey(psw, getPublicKey())
                val post = cursor.getString(cursor.getColumnIndex("emp_position"))
                val ID = cursor.getInt(cursor.getColumnIndex("emp_id"))

                //var id = cursor.getInt(cursor.getColumnIndex("id"))
                cursor.close()

                if (decryptByPublicKey == password && flag == true) {
                    val editor = prefs.edit()
                    if (rememberPass.isChecked){
                        editor.putBoolean("remember_password",true)
                        editor.putString("account",account)
                        editor.putString("password",password)
                    }else{
                        editor.clear()
                    }
                    editor.apply()
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
                    db.execSQL(
                        "insert into log(behavior,object,time,date) values('login',?,time('now'),date('now'))",
                        arrayOf(ID)
                    )
                    if (post == "staff") {
                        val intent = Intent(this, staffview::class.java)
                        startActivity(intent)

                    } else {
                        val intent = Intent(this, managerview::class.java)
                        startActivity(intent)
                    }
                } else {
                    AlertDialog.Builder(this).apply {
                        setTitle("错误")
                        setMessage("请重新输入")
                        setCancelable(false)
                        setPositiveButton("Yes") { _, _ ->
                            finish()
                            val intent = Intent(context, LoginActivity::class.java)
                            startActivity(intent)
                        }
                        show()
                    }
                }
            } catch (e: Exception) {
                AlertDialog.Builder(this).apply {
                    setTitle("错误")
                    setMessage("请重新输入")
                    setCancelable(false)
                    setPositiveButton("Yes") { _, _ ->
                        finish()
                        val intent = Intent(context, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    show()
                }
            }
        }




        New.setOnClickListener {
            //转至new_user_activity
            val intent = Intent(this, NewUser_activity::class.java)
            startActivity(intent)

        }

        Recover.setOnClickListener {
            val intent = Intent(this, recover::class.java)
            startActivity(intent)
        }

    }

}
