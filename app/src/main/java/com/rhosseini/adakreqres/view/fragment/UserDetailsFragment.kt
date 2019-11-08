package com.rhosseini.adakreqres.view.fragment


import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import com.rhosseini.adakreqres.R
import com.rhosseini.adakreqres.databinding.FragmentUserDetailsBinding


class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_details, container, false)
        binding.lifecycleOwner = this


        return binding.root
    }

}
