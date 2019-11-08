package com.rhosseini.adakreqres.view.fragment


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.rhosseini.adakreqres.R
import com.rhosseini.adakreqres.databinding.FragmentAddUserBinding


class AddUserFragment : Fragment() {

    private lateinit var binding: FragmentAddUserBinding
    private val safeArgs: AddUserFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_user, container, false)
        binding.lifecycleOwner = this

        binding.etName.setText(safeArgs.user?.first_name)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_user, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionSave -> Toast.makeText(activity, "save", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
