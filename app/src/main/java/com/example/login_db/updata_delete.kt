package com.example.login_db

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_updata_delete.*
import kotlinx.android.synthetic.main.activity_updata_delete.manager
import kotlinx.android.synthetic.main.activity_updata_delete.staff

class updata_delete : AppCompatActivity(), View.OnClickListener {
    var emp_Position = "staff"
    override fun onClick(v: View?) {
        when (v?.id) {
            staff.id -> emp_Position = "staff"
            manager.id -> emp_Position = "manager"
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updata_delete)
        staff.setOnClickListener(this)
        manager.setOnClickListener(this)
        val dbHelper = MyDatabaseHelper(this, "Login.db", 1)
        val db = dbHelper.writableDatabase
        val uf: userfactory = userfactory()
        val users: MutableList<user> = uf.createuser(db)
        val recyclerView: RecyclerView = findViewById(R.id.rv_user)
        //设置LayoutManager
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        //设置动画效果
        val itemAnimator = DefaultItemAnimator()
        recyclerView.itemAnimator = itemAnimator
        //设置适配器
        val UAdapter: userAdapter = userAdapter(users)
        recyclerView.adapter = UAdapter
        //添加默认的分割线
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)


        button_del.setOnClickListener {
            val emp_Id = Integer.parseInt(empIdEdit.text.toString())


            AlertDialog.Builder(this).apply {
                setTitle("删除操作")
                setMessage("确认删除？")
                setCancelable(false)
                setPositiveButton("Yes") { _, _ ->

                    db.execSQL(
                        "delete from empinfo where emp_id = ? ",
                        arrayOf(emp_Id)
                    )
                    finish()
                    val intent = Intent(context, updata_delete::class.java)
                    startActivity(intent)
                }

                setNegativeButton("No") { _, _ ->
                    finish()
                    val intent = Intent(context, updata_delete::class.java)
                    startActivity(intent)
                }
                show()
            }

        }

        button_upd.setOnClickListener {
            val emp_Id = Integer.parseInt(empIdEdit.text.toString())
            val department_Id = Integer.parseInt(depIdEdit.text.toString())
            Toast.makeText(this, "aaaa" + emp_Position, Toast.LENGTH_SHORT).show()
            AlertDialog.Builder(this).apply {
                setTitle("修改操作")
                setMessage("确认修改？")
                setCancelable(false)
                setPositiveButton("Yes") { _, _ ->
                    db.execSQL(
                        "update empinfo set department_id = ? , emp_position = ? where emp_id = ?",
                        arrayOf(department_Id , emp_Position , emp_Id)
                    )
                    finish()
                    val intent = Intent(context, updata_delete::class.java)
                    startActivity(intent)
                }
                setNegativeButton("No") { _, _ ->
                    finish()
                    val intent = Intent(context, updata_delete::class.java)
                    startActivity(intent)
                }
                show()
            }

        }
    }
}




