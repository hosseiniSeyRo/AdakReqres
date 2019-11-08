package com.rhosseini.adakreqres.model.webService.model.repository;


import androidx.lifecycle.MutableLiveData;

import com.rhosseini.adakreqres.model.webService.ReqresService;
import com.rhosseini.adakreqres.model.webService.RetrofitClient;
import com.rhosseini.adakreqres.model.webService.model.model.ResponseWrapper;
import com.rhosseini.adakreqres.model.webService.model.model.UserResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReqresRepository {

    private static ReqresRepository reqresRepository;
    private ReqresService reqresService;
    public MutableLiveData<ResponseWrapper<UserResponse>> allUserLiveData = new MutableLiveData<>();


    public static ReqresRepository getInstance() {
        if (reqresRepository == null) {
            reqresRepository = new ReqresRepository();
        }
        return reqresRepository;
    }


    private ReqresRepository() {
        this.reqresService = RetrofitClient.createService(ReqresService.class);
    }


    /* get all User */
    public void getAllUsers(int page) {
        // set loading status
        allUserLiveData.setValue(ResponseWrapper.loading());

        // fetch data
        reqresService.getAllUser(page).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NotNull Call<UserResponse> call, @NotNull Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    // set response value
                    allUserLiveData.setValue(ResponseWrapper.success(response.body()));
                } else {
                    allUserLiveData.setValue(ResponseWrapper.error(new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserResponse> call, @NotNull Throwable t) {
                allUserLiveData.setValue(ResponseWrapper.error(t));
            }
        });
    }

}
