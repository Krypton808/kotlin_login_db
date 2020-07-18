package com.example.login_db

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_managerview.*

class managerview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managerview)
        hello.setText("Manager UI")

        emp_Upd_Del.setOnClickListener{
            val intent = Intent(this,updata_delete::class.java)
            startActivity(intent)
        }

        dep_Upd_Del.setOnClickListener {
            val intent = Intent(this,dep_update_delete::class.java)
            startActivity(intent)
        }

        showlog.setOnClickListener {
            val intent = Intent(this,log_show::class.java)
            startActivity(intent)
        }
    }
}
