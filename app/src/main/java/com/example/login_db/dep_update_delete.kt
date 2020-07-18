package com.example.login_db

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_dep_update_delete.*
import java.lang.Exception

class dep_update_delete : AppCompatActivity() {


    val dbHelper = MyDatabaseHelper(this, "Login.db", 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dep_update_delete)
        try {
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
            list1.adapter = adapter
            val db = dbHelper.writableDatabase
            val cursor = db.rawQuery("select* from departmentinfo", null)

            if (cursor.moveToFirst()) {
                do {
                    val str1 = cursor.getString(cursor.getColumnIndex("department_id"))
                    val str2 = cursor.getString(cursor.getColumnIndex("department_name"))
                    val Str = "仓库编号： " + str1 + "                    " + "仓库名称： " + str2
                    adapter.add(Str)
                } while (cursor.moveToNext())
            }
            cursor.close()

            button_dep_upd.setOnClickListener {
                val depId = Integer.parseInt(departmentIdEdit.text.toString())
                val depName = departmentNameEdit.text.toString()
                AlertDialog.Builder(this).apply {
                    setTitle("修改操作")
                    setMessage("确认修改？")
                    setCancelable(false)
                    setPositiveButton("Yes") { _, _ ->
                        db.execSQL(
                            "update departmentinfo set department_name = ?  where department_id = ?",
                            arrayOf(depName, depId)
                        )
                        finish()
                        val intent = Intent(context, dep_update_delete::class.java)
                        startActivity(intent)
                    }
                    setNegativeButton("No") { _, _ ->
                        finish()
                        val intent = Intent(context, dep_update_delete::class.java)
                        startActivity(intent)
                    }
                    show()
                }
            }

            button_dep_del.setOnClickListener {
                val depId = Integer.parseInt(departmentIdEdit.text.toString())
                AlertDialog.Builder(this).apply {
                    setTitle("删除操作")
                    setMessage("确认删除？")
                    setCancelable(false)
                    setPositiveButton("Yes") { _, _ ->

                        db.execSQL(
                            "delete from departmentinfo where department_id = ? ",
                            arrayOf(depId)
                        )
                        finish()
                        val intent = Intent(context, dep_update_delete::class.java)
                        startActivity(intent)
                    }

                    setNegativeButton("No") { _, _ ->
                        finish()
                        val intent = Intent(context, dep_update_delete::class.java)
                        startActivity(intent)
                    }
                    show()
                }
            }

            button_dep_cre.setOnClickListener {
                val depName = departmentNameEdit.text.toString()
                AlertDialog.Builder(this).apply {
                    setTitle("创建操作")
                    setMessage("确认创建？")
                    setCancelable(false)
                    setPositiveButton("Yes") { _, _ ->

                        db.execSQL(
                            "insert into departmentinfo(department_name) values(?)",
                            arrayOf(depName)
                        )
                        finish()
                        val intent = Intent(context, dep_update_delete::class.java)
                        startActivity(intent)
                    }

                    setNegativeButton("No") { _, _ ->
                        finish()
                        val intent = Intent(context, dep_update_delete::class.java)
                        startActivity(intent)
                    }
                    show()
                }
            }
        }catch (e:Exception){
            AlertDialog.Builder(this).apply {
                setTitle("输入")
                setMessage("请重新输入")
                setCancelable(false)
                setPositiveButton("Yes") { _, _ ->
                    finish()
                    val intent = Intent(context, dep_update_delete::class.java)
                    startActivity(intent)
                }
                show()
            }
        }
        }
    }

