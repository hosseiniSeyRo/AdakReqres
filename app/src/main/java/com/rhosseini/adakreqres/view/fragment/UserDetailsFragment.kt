package com.rhosseini.adakreqres.view.fragment


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.rhosseini.adakreqres.R
import com.rhosseini.adakreqres.databinding.FragmentUserDetailsBinding
import com.rhosseini.adakreqres.model.webService.model.model.ResponseWrapper
import com.rhosseini.adakreqres.model.webService.model.model.ResponseWrapper.Status
import com.rhosseini.adakreqres.viewModel.UserDetailViewModel


class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private val safeArgs: UserDetailsFragmentArgs by navArgs()
    private lateinit var viewModel: UserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_details, container, false)
        binding.lifecycleOwner = this

        /* init viewModel */
        viewModel = ViewModelProviders.of(this).get(UserDetailViewModel::class.java)

        binding.user = safeArgs.user

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_user_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionEdit -> findNavController().navigate(
                UserDetailsFragmentDirections.actionUserDetailsToAddUser()
            )
            R.id.actionDelete -> deleteUser(safeArgs.user.id, item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser(userId: Int, item: MenuItem) {
        viewModel.deleteUser(userId).observe(this, Observer { response ->
            consumeAllUsersResponse(response, item)
        })
    }

    private fun consumeAllUsersResponse(
        response: ResponseWrapper<Void>,
        item: MenuItem
    ) {
        when (response.status) {
            Status.LOADING -> item.setIcon(R.drawable.ic_refresh)
            Status.SUCCESS -> {
                item.setIcon(R.drawable.ic_delete)
                Toast.makeText(activity, "delete", Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            }
            Status.ERROR -> {
                item.setIcon(R.drawable.ic_delete)
                Toast.makeText(activity, "error: " + response.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

}
