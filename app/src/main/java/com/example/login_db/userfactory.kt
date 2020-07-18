package com.example.login_db

import android.database.sqlite.SQLiteDatabase

class userfactory {
    var arr_emp_id = arrayOfNulls<Int>(100)
    var arr_emp_name = arrayOfNulls<String>(100)
    var arr_department_id = arrayOfNulls<Int>(100)
    var arr_emp_position = arrayOfNulls<String>(100)
    var arr_img = arrayOfNulls<ByteArray>(100)
    var usercount = 0

    fun queryDatabase(db: SQLiteDatabase){
        val cursor = db.rawQuery("select* from empinfo,empImage where empinfo.emp_id = empImage.emp_id ",null)
        usercount = cursor.count
        for(i in 0 until cursor.count){
            if (cursor.moveToFirst()){
                cursor.move(i)
                arr_emp_id[i] = cursor.getInt(cursor.getColumnIndex("emp_id"))
                arr_emp_name[i] = cursor.getString(cursor.getColumnIndex("emp_name"))
                arr_department_id[i] = cursor.getInt(cursor.getColumnIndex("department_id"))
                arr_emp_position[i] = cursor.getString(cursor.getColumnIndex("emp_position"))
                arr_img[i] = cursor.getBlob(cursor.getColumnIndex("IMAGE"))
            }
        }
        cursor.close()
    }

    fun  createuser(db:SQLiteDatabase):MutableList<user>{
        var users :MutableList<user>  = mutableListOf()

        queryDatabase(db)
        for (i in 0 until usercount){
            var emp_id = arr_emp_id[i]
            var emp_name = arr_emp_name[i]
            var department_id = arr_department_id[i]
            var emp_position = arr_emp_position[i]
            var img = arr_img[i]


            users.add(user(emp_id, emp_name, department_id, emp_position, img))
        }
        return users
    }
}