package com.rhosseini.adakreqres.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rhosseini.adakreqres.model.webService.model.model.ResponseWrapper
import com.rhosseini.adakreqres.model.webService.model.model.UserResponse
import com.rhosseini.adakreqres.model.webService.model.repository.ReqresRepository

class UserListViewModel: ViewModel() {

    private val repository = ReqresRepository.getInstance()

    val allUserLiveData: MutableLiveData<ResponseWrapper<UserResponse>> = repository.allUserLiveData

    /* get all Users */
    fun getAllUsers(page: Int) {
        repository.getAllUsers(page)
    }
}