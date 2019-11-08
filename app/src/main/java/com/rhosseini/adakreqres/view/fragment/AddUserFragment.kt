package com.rhosseini.adakreqres.view.fragment


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.rhosseini.adakreqres.R
import com.rhosseini.adakreqres.databinding.FragmentAddUserBinding
import com.rhosseini.adakreqres.model.webService.model.AddNewUserResponse
import com.rhosseini.adakreqres.model.webService.model.ResponseWrapper
import com.rhosseini.adakreqres.model.webService.model.ResponseWrapper.Status
import com.rhosseini.adakreqres.model.webService.model.UpdateUserResponse
import com.rhosseini.adakreqres.viewModel.AddUserViewModel


class AddUserFragment : Fragment() {

    private lateinit var binding: FragmentAddUserBinding
    private val safeArgs: AddUserFragmentArgs by navArgs()
    private lateinit var viewModel: AddUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        /* init viewModel */
        viewModel = ViewModelProviders.of(this).get(AddUserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_user, container, false)
        binding.lifecycleOwner = this

        if (safeArgs.user != null) {
            // set title
            activity?.title = getString(R.string.pageTitleUpdateUser)

            binding.etName.setText(safeArgs.user?.first_name)
        } else {
            // set title
            activity?.title = getString(R.string.pageTitleAddUser)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_user, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionSave -> {
                val name = binding.etName.text?.trim().toString()
                val job = binding.etJob.text?.trim().toString()

                if (viewModel.validationInputs(name, job)) {
                    save(name, job, item)
                } else {
                    Toast.makeText(activity, "! validate inputs", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun save(name: String, job: String, item: MenuItem) {
        if (safeArgs.user == null) { // add new user
            viewModel.addNewUser(name, job).observe(this, Observer { response ->
                consumeAddUserResponse(response, item)
            })
        } else { // update user
            val id = safeArgs.user!!.id
            val user = UpdateUserResponse(name, job)
            viewModel.updateUser(id, user).observe(this, Observer { response ->
                consumeUpdateUserResponse(response, item)
            })
        }

    }

    private fun consumeAddUserResponse(
        response: ResponseWrapper<AddNewUserResponse>,
        item: MenuItem
    ) {
        when (response.status) {
            Status.LOADING -> item.setIcon(R.drawable.ic_refresh)
            Status.SUCCESS -> {
                item.setIcon(R.drawable.ic_save)
                Toast.makeText(activity, "add user by id: "+ response.data?.id, Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            }
            Status.ERROR -> {
                item.setIcon(R.drawable.ic_save)
                Toast.makeText(activity, "error: " + response.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun consumeUpdateUserResponse(
        response: ResponseWrapper<UpdateUserResponse>,
        item: MenuItem
    ) {
        when (response.status) {
            Status.LOADING -> item.setIcon(R.drawable.ic_refresh)
            Status.SUCCESS -> {
                item.setIcon(R.drawable.ic_save)
                Toast.makeText(activity, "update user by name: "+ response.data?.name, Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            }
            Status.ERROR -> {
                item.setIcon(R.drawable.ic_save)
                Toast.makeText(activity, "error: " + response.error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
