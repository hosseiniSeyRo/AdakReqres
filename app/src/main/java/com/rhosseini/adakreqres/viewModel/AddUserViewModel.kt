package com.rhosseini.adakreqres.viewModel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rhosseini.adakreqres.model.webService.model.model.AddNewUserResponse
import com.rhosseini.adakreqres.model.webService.model.model.ResponseWrapper
import com.rhosseini.adakreqres.model.webService.model.model.UpdateUserResponse
import com.rhosseini.adakreqres.model.webService.model.repository.ReqresRepository

class AddUserViewModel : ViewModel() {

    private val repository = ReqresRepository.getInstance()

    fun validationInputs(name: String, job: String): Boolean {
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(job)) {
            return false
        }
        return true
    }

    fun addNewUser(name: String, job: String): MutableLiveData<ResponseWrapper<AddNewUserResponse>> {
        return repository.addNewUser(name, job)
    }

    fun updateUser(id: Int, user: UpdateUserResponse): MutableLiveData<ResponseWrapper<UpdateUserResponse>> {
        return repository.updateUser(id, user)
    }
}