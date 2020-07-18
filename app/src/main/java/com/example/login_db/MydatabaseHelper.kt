package com.example.login_db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {
    /**  部门信息表
     *   department_id   部门编号
     *   department_name 部门名称
     */
    private val createDEP =
        "CREATE TABLE IF NOT EXISTS  departmentinfo (department_id integer primary key autoincrement," +
                "department_name varchar(20) unique " +
                "check(department_name like'%_dep'))"


    /**  员工信息表
     *   emp_id          员工编号
     *   emp_name;       员工名称
     *   department_id   部门编号
     *   emp_position    职位
     *   account         账号
     *   password        密码
     *   phone           手机
     */
    private val createEMP =
        "CREATE TABLE IF NOT EXISTS empinfo (emp_id integer primary key autoincrement," +
                "account text unique," +
                "password text," +
                "phone varchar(11)," +
                "emp_name varchar(20)," +
                "emp_position text," +
                "department_id integer " +
                "check(length(account)<10), " +
                "check(length(phone) = 11))"
    //"check(email like '%@%'))"


    /**  头像表
     *   emp_id          员工编号
     *   IMAGE           二进制图像
     */
    private val createIMG =
        "CREATE TABLE IF NOT EXISTS empImage(emp_id integer primary key autoincrement," +
                "IMAGE BLOB)"


    /**  日志表
     *   behavior        行为
     *   object          对象
     *   time            时间
     *   date            日期
     */

    private val createlog =
        "CREATE TABLE IF NOT EXISTS log(log_id integer primary key autoincrement," +
                "behavior text," +
                "object integer, " +
                "time text," +
                "date text)"


    //check department_name 以_dep结尾
   /* private val createcheck1 = "alter table departmentinfo" +
            "add constraint ck_department_name check(department_name like'%_dep')"

    //check account长度<=10
    private val createcheck2 = "alter table empinfo" +
            "add constraint ck_account check(len(account)<=10)"

    //check emp_position 为 staff 或 manager
    private val createcheck3 = "alter table empinfo" +
            "add constraint ck_emp_position check(emp_position = 'staff' or emp_position = 'manager')"

    //check email 中有@
    private val createcheck4 = "alter table empinfo" +
            "add constraint ck_email check(email like '%@%')"*/

    //empinfo视图 包含emp_id , emp_name , department_id , emp_position
    private val createview1 = "create view v_empinfo1 " +
            "as " +
            "select emp_id , emp_name , department_id , emp_position " +
            "from empinfo;"

    //empinfo视图 包含emp_id , account , password , phone
    private val createview2 = "create view v_empinfo2 " +
            "as " +
            "select emp_id , account , password , phone " +
            "from empinfo;"

    //empinfo视图 包含emp_id , emp_name , department_id , emp_position , account , password , phone
    private val createview3 = "create view v_empinfo3 " +
            "as " +
            "select emp_id , emp_name , department_id , emp_position , account , password , phone " +
            "from empinfo;"

    private val createview4 = "create view v_show_log " +
            "as " +
            "select behavior , time , date " +
            "from log;"

    //当emp_info插入员工信息时，先要检查部门信息表中是否有该部门编号
    private val createtrigger1 = "create trigger emp_insert " +
            "before insert on empinfo " +
            "for each row " +
            "begin " +
            "select raise(rollback,'on department_id') " +
            "where (select department_id from departmentinfo where department_id=new.department_id) is null; " +
            "end;"

    //当emp_info更新员工信息时，先要检查部门信息表中是否有该部门编号
    private val createtrigger2 = "create trigger emp_update " +
            "before update on empinfo " +
            "for each row " +
            "begin " +
            "select raise(rollback,'on department_id') " +
            "where (select department_id from departmentinfo where department_id=new.department_id) is null; " +
            "end;"

    //当删除部门信息表中某一行数据时，相应的在empinfo表中对应的员工也应该删除
    private val createtrigger3 = "create trigger dep_delete " +
            "before delete on departmentinfo " +
            "for each row " +
            "begin " +
            "delete from empinfo where department_id=old.department_id;  " +
            "end;"

    //当删除员工信息表中某一行数据时，相应的在empImage表中对应的员工也应该删除
    private val createtrigger4 = "create trigger emp_delete " +
            "before delete on empinfo " +
            "for each row " +
            "begin " +
            "delete from empImage where emp_id=old.emp_id;  " +
            "end;"

    //日志记录对empinfo的update
    private val createtrigger5 = "create trigger log_update " +
            "after update on empinfo " +
            "for each row " +
            "begin " +
            "insert into log(behavior,time,date) values('update',time('now'),date('now')); " +
            "end;"

    //日志记录对empinfo的insert
    private val createtrigger6 = "create trigger log_insert " +
            "after insert on empinfo " +
            "for each row " +
            "begin " +
            "insert into log(behavior,time,date) values('insert',time('now'),date('now')); " +
            "end;"

    //日志记录对empinfo的delete
    private val createtrigger7 = "create trigger log_delete " +
            "after delete on empinfo " +
            "for each row " +
            "begin " +
            "insert into log(behavior,time,date) values('insert',time('now'),date('now')); " +
            "end;"


    //通过emp_id删除员工
    /*private val createprocedure1 = "create procedure emp_del" +
            "@emp_id integer" +
            "as" +
            "delete from empinfo where emp_id = @emp_id" +
            "go;"

    //更新v_empinfo1/empinfo 里的department_id
    private val createprocedure2 = "create procedure emp_update" +
            "@emp_id integer , @department_id integer " +
            "as" +
            "update v_empinfo1" +
            "set department_id = @department_id" +
            "where emp_id = @emp_id" +
            "go;"

    //更新v_empinfo1/empinfo 里的emp_position
    private val createprocedure3 = "create procedure emp_update" +
            "@emp_id integer , @emp_position text " +
            "as" +
            "update v_empinfo1" +
            "set emp_position = @emp_position" +
            "where emp_id = @emp_id" +
            "go;"*/


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createDEP)
        db.execSQL(createEMP)
        db.execSQL(createIMG)
        db.execSQL(createlog)
        // db.execSQL(createcheck1)
        // db.execSQL(createcheck2)
        //db.execSQL(createcheck3)
        //db.execSQL(createcheck4)
        db.execSQL(createview1)
        db.execSQL(createview2)
        db.execSQL(createview3)
        db.execSQL(createview4)
        db.execSQL(createtrigger1)
        db.execSQL(createtrigger2)
        db.execSQL(createtrigger3)
        db.execSQL(createtrigger4)
        db.execSQL(createtrigger5)
        db.execSQL(createtrigger6)
        db.execSQL(createtrigger7)
        //db.execSQL(createprocedure1)
        //db.execSQL(createprocedure2)
        //db.execSQL(createprocedure3)


        Toast.makeText(context, "Database create succeeded", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}