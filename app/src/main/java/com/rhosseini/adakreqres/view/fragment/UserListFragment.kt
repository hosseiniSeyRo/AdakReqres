package com.rhosseini.adakreqres.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.rhosseini.adakreqres.R
import com.rhosseini.adakreqres.databinding.FragmentUserListBinding
import com.rhosseini.adakreqres.model.webService.model.User
import com.rhosseini.adakreqres.view.adapter.BaseAdapter
import com.rhosseini.adakreqres.view.adapter.UserAdapter

class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private val userList = ArrayList<User>()
    private lateinit var userAdapter: UserAdapter
    private lateinit var rvUser: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setDummyData()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initUserRecyclerView()
    }

    private fun setDummyData() {
        userList.addAll(
            listOf(
                User(
                    2,
                    "tehran",
                    "iran",
                    "jg@hb.dsf",
                    "https://s3.amazonaws.com/uifaces/faces/twitter/araa3185/128.jpg"
                ),
                User(
                    3,
                    "hamid",
                    "iran",
                    "jg@hb.dsf",
                    "https://s3.amazonaws.com/uifaces/faces/twitter/follettkyle/128.jpg"
                ),
                User(
                    2,
                    "samad kjnnnnnnnnnnnnnkkjkkkkkkkkkkk",
                    "iran kjnkkkkkkkkkkkkkkkk",
                    "jg@hb.dsfjjjjbbbbkjjjjjjjbbbbbbbbbbbbbbbbb",
                    "https://s3.amazonaws.com/uifaces/faces/twitter/follettkyle/128.jpg"
                ),
                User(
                    5,
                    "rastin",
                    "iran",
                    "jg@hb.dsf",
                    "https://s3.amazonaws.com/uifaces/faces/twitter/araa3185/128.jpg"
                ),
                User(
                    2,
                    "tehran",
                    "iran",
                    "jg@hb.dsf",
                    "https://s3.amazonaws.com/uifaces/faces/twitter/araa3185/128.jpg"
                )
            )
        )
    }

    private fun initUserRecyclerView() {
        rvUser = binding.rvUser
        userAdapter = UserAdapter(object : BaseAdapter.OnItemClickListener<User> {
            override fun onItemClick(item: User) {
                Toast.makeText(activity, "" + item.first_name, Toast.LENGTH_SHORT).show()
            }
        })
//        rvUser.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvUser.layoutManager = GridLayoutManager(activity, 2)

        rvUser.adapter = userAdapter

        userAdapter.addData(userList)
    }
}
