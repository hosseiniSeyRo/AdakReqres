package com.rhosseini.adakreqres.view.fragment


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.Constraints
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rhosseini.adakreqres.R
import com.rhosseini.adakreqres.databinding.FragmentUserListBinding
import com.rhosseini.adakreqres.model.webService.model.model.ResponseWrapper
import com.rhosseini.adakreqres.model.webService.model.model.ResponseWrapper.Status
import com.rhosseini.adakreqres.model.webService.model.model.User
import com.rhosseini.adakreqres.model.webService.model.model.UserResponse
import com.rhosseini.adakreqres.view.adapter.BaseAdapter
import com.rhosseini.adakreqres.view.adapter.UserAdapter
import com.rhosseini.adakreqres.viewModel.UserListViewModel
import java.util.*


class UserListFragment : Fragment() {

    private val TAG = "rHosseini" + UserListFragment::getTag
    private lateinit var binding: FragmentUserListBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var rvUser: RecyclerView
    private var userList: MutableList<User>? = null
    private lateinit var loadingLayout: View
    private lateinit var emptyLayout: View
    private var currentPage = 1
    private var hasNextPage = false
    private var isLoading = false
    //    private var viewModel by lazy { ViewModelProvider(this).get(UserListViewModel::class.java) }
    private lateinit var viewModel: UserListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* init viewModel */
        viewModel = ViewModelProviders.of(this).get(UserListViewModel::class.java)

        observeViewModel(viewModel)

        /* init recyclerView adapter */
        userAdapter = UserAdapter(object : BaseAdapter.OnItemClickListener<User> {
            override fun onItemClick(item: User) {
                Log.wtf(TAG, "userId= " + item.id)
                findNavController().navigate(
                    UserListFragmentDirections.actionUserListToUserDetails(item)
                )
            }
        })

        /* get all Users */
        getAllUsers(currentPage)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false)
        binding.lifecycleOwner = this

        // set title
        activity?.title = getString(R.string.pageTitleUserList)

        binding.fab.setColorFilter(Color.WHITE)
        binding.fab.setOnClickListener {
            val action = UserListFragmentDirections.actionUserListToAddUser(null)
            findNavController().navigate(action)
        }

        /* init recyclerView */
        initUserRecyclerView()

        initViews()

        /* Handle pagination */
        rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && !isLoading) {
                    if (hasNextPage) {
                        currentPage += 1
                        getAllUsers(currentPage)
                    }
                }
            }

        })

        binding.swipeRefresh?.setOnRefreshListener {
            currentPage = 1
            getAllUsers(currentPage)

            // Notify swipeRefreshLayout that the refresh has finished
            binding.swipeRefresh.isRefreshing = false
        }

        return binding.root
    }


    private fun initViews() {
        loadingLayout = binding.loadingLayout
        emptyLayout = binding.emptyLayout

        if (userAdapter.itemCount != 0) {
            loadingLayout.visibility = View.GONE
            emptyLayout.visibility = View.GONE
        }
    }

    private fun initUserRecyclerView() {
        rvUser = binding.rvUser
        rvUser.layoutManager = GridLayoutManager(activity, 2)

        rvUser.adapter = userAdapter
    }

    private fun getAllUsers(page: Int) {
        viewModel.getAllUsers(page)
    }

    private fun observeViewModel(viewModel: UserListViewModel) {
        viewModel.allUserLiveData.observe(this, Observer {
            consumeAllUsersResponse(it, currentPage)
        })
    }

    /* consume earthquakeList response */
    private fun consumeAllUsersResponse(response: ResponseWrapper<UserResponse>, page: Int) {
        // remove rv data for swipeRefreshing time
        if (binding.swipeRefresh.isRefreshing) {
            userList?.clear()
            userAdapter.removeAllData()
        }

        Log.wtf(TAG, "response.status= " + response.status)
        isLoading = false

        when (response.status) {
            Status.LOADING -> {
                if (page == 1) {
                    // change recyclerView status
                    // show loadingLayout and hide recyclerView and emptyLayout
                    loadingLayout.visibility = View.VISIBLE
                    rvUser.visibility = View.GONE
                    emptyLayout.visibility = View.GONE
                }
            }
            Status.SUCCESS -> {
                hasNextPage = response.data?.totalPages != page

                userList = response.data!!.userList as MutableList<User>
                userAdapter.addData(userList)

                // check recyclerView item count
                if (userAdapter.itemCount == 0) {
                    // change recyclerView status
                    // if recyclerView item count=0, hide recyclerView and loadingLayout and show emptyLayout
                    loadingLayout.visibility = View.GONE
                    rvUser.visibility = View.GONE
                    emptyLayout.visibility = View.VISIBLE
                } else {
                    // change recyclerView status
                    // if recyclerView item count!=0, hide loadingLayout and emptyLayout and show recyclerView
                    loadingLayout.visibility = View.GONE
                    rvUser.visibility = View.VISIBLE
                    emptyLayout.visibility = View.GONE
                }
            }
            Status.ERROR -> {
                if (page == 1) {
                    // change recyclerView status
                    // hide loadingLayout and recyclerView and emptyLayout
                    loadingLayout.visibility = View.GONE
                    rvUser.visibility = View.GONE
                    emptyLayout.visibility = View.GONE
                }

                // show error message
                if (response.error != null) {
                    Toast.makeText(
                        activity,
                        " " + response.error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(Constraints.TAG, Objects.requireNonNull(response.error.message))
                }
            }
        }
    }
}
