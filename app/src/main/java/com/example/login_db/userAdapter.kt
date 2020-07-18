package com.example.login_db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalArgumentException

class userAdapter(val userList: MutableList<user>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val user_image: ImageView = view.findViewById(R.id.iv_user_image)
        val user_id: TextView = view.findViewById(R.id.tv_user_id)
        val user_name: TextView = view.findViewById(R.id.tv_user_name)
        val department_id: TextView = view.findViewById(R.id.tv_department_id)
        val user_position: TextView = view.findViewById(R.id.tv_user_position)
    }

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_layout, parent, false)
        val userHolder = ViewHolder(view)
        return userHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val U:user = userList[position]
        when(holder){
            is ViewHolder -> {
                holder.user_id.text = "用户id："+U.emp_id.toString()
                holder.user_name.text = "用户名字:"+U.emp_name
                holder.department_id.text = "Dep id: "+U.department_id.toString()
                holder.user_position.text ="职位："+ U.emp_position

                val PM: PhotoManagement = PhotoManagement()
                val ibv = PM.getBmp(position)
                val bd = PM.change_to_drawable(ibv)

                holder.user_image.setImageDrawable(bd)
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}



