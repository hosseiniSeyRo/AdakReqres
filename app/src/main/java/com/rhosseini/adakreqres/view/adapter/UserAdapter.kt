package com.rhosseini.adakreqres.view.adapter


import com.rhosseini.adakreqres.R
import com.rhosseini.adakreqres.model.webService.model.model.User

class UserAdapter(itemClickListener: OnItemClickListener<User>) :
    BaseAdapter<User>(itemClickListener) {

//    private lateinit var data: List<User>
    private val data: MutableList<User> = ArrayList()

    override fun getItemCount(): Int = data.size

    override fun getItemForPosition(position: Int): User = data[position]

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.row_user
    }

    fun addData(data: MutableList<User>?) {
        if (data != null) {
            this.data.addAll(data)
            notifyDataSetChanged()
        }
    }

    fun removeAllData() {
        this.data.clear()
        notifyDataSetChanged()
    }
}