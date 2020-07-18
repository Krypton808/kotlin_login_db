package com.example.login_db

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_log_show.*
import kotlinx.android.synthetic.main.activity_login.*

class log_show : AppCompatActivity() {
    val dbHelper = MyDatabaseHelper(this, "Login.db", 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_show)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        loglist.adapter = adapter
        val db = dbHelper.writableDatabase
        var cursor = db.rawQuery("select* from log", null)

        if (cursor.moveToFirst()) {
            do {

                val str1 = cursor.getString(cursor.getColumnIndex("behavior"))
                val str2 = cursor.getString(cursor.getColumnIndex("object"))
                val str3 = cursor.getString(cursor.getColumnIndex("time"))
                val str4 = cursor.getString(cursor.getColumnIndex("date"))
                if (str2 != null){
                    val Str =
                        "行为：" + str1 + "   " + "用户：" + str2 + "    " + "时间：" + str3 + "    " + "日期：" + str4
                    adapter.add(Str)
                }else{
                    val Str =
                        "行为：" + str1 + "   " + "用户：" + "*" + "    " + "时间：" + str3 + "    " + "日期：" + str4
                    adapter.add(Str)
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        var cursor2 = db.rawQuery("select count(*) 总数  from log where behavior = ?", arrayOf("login"))
        cursor2.moveToLast()
        val str5 = cursor2.getInt(cursor2.getColumnIndex("总数")).toString()
        amount.setText(str5)
    }
}
