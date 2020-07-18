package com.example.login_db

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_new_user_activity.*
import kotlinx.android.synthetic.main.activity_recover.*
import java.lang.Exception

class recover : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover)
        val dbHelper = MyDatabaseHelper(this, "Login.db", 1)
        confirm.setOnClickListener {
           // try {
                val account = rec_accountEdit.text.toString()
                val phone = rec_phoneEdit.text.toString()
                val password = new_psw_Edit.text.toString()
                val encrypt = RSACrypt.encryptByPrivateKey(password, RSACrypt.getPrivateKey())
                val db = dbHelper.writableDatabase
                val cursor =
                    db.rawQuery("select phone from v_empinfo3 where account = ?", arrayOf(account))
                cursor.moveToFirst()
                val p = cursor.getString(cursor.getColumnIndex("phone")).toString()
                cursor.close()
                Toast.makeText(this, "aaa"+ p.toString() + phone.toString()+"aaa", Toast.LENGTH_LONG).show()

                if (p == phone ) {
                    db.execSQL(
                        "update empinfo set password = ? where account = ? ",
                        arrayOf(encrypt, account)
                    )


                    AlertDialog.Builder(this).apply {
                        setTitle("成功")
                        setMessage("修改成功")
                        setCancelable(false)
                        setPositiveButton("Yes") { _, _ ->
                            finish()
                            val intent = Intent(context, LoginActivity::class.java)
                            startActivity(intent)
                        }
                        show()
                    }


                } else {

                    AlertDialog.Builder(this).apply {
                        setTitle("错误")
                        setMessage("请重新输入")
                        setCancelable(false)
                        setPositiveButton("Yes") { _, _ ->
                            finish()
                            val intent = Intent(context, recover::class.java)
                            startActivity(intent)
                        }
                        show()
                    }

                }
            /*} catch (e: Exception) {
                AlertDialog.Builder(this).apply {
                    setTitle("账号不存在")
                    setMessage("请重新输入Account")
                    setCancelable(false)
                    setPositiveButton("Yes") { _, _ ->
                        finish()
                        val intent = Intent(context, recover::class.java)
                        startActivity(intent)
                    }
                    show()
                }
            }*/
        }


    }
}
