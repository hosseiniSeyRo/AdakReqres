package com.rhosseini.adakreqres.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rhosseini.adakreqres.model.repository.ReqresRepository
import com.rhosseini.adakreqres.model.webService.model.ResponseWrapper

class UserDetailViewModel : ViewModel() {

    private val repository = ReqresRepository.getInstance()

    /* delete User */
    fun deleteUser(userId: Int): MutableLiveData<ResponseWrapper<Void>> {
        return repository.deleteUser(userId)
    }
}