package com.rhosseini.adakreqres.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rhosseini.adakreqres.model.webService.model.model.ResponseWrapper
import com.rhosseini.adakreqres.model.webService.model.repository.ReqresRepository

class UserDetailViewModel : ViewModel() {

    private val repository = ReqresRepository.getInstance()

    /* delete User */
    fun deleteUser(userId: Int): MutableLiveData<ResponseWrapper<Void>> {
        return repository.deleteUser(userId)
    }
}