package com.rhosseini.adakreqres.model.repository;


import androidx.lifecycle.MutableLiveData;

import com.rhosseini.adakreqres.model.webService.ReqresService;
import com.rhosseini.adakreqres.model.webService.RetrofitClient;
import com.rhosseini.adakreqres.model.webService.model.AddNewUserResponse;
import com.rhosseini.adakreqres.model.webService.model.UpdateUserResponse;
import com.rhosseini.adakreqres.model.webService.model.UserResponse;
import com.rhosseini.adakreqres.model.webService.model.ResponseWrapper;

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

    /* delete user */
    public MutableLiveData<ResponseWrapper<Void>> deleteUser(int userId) {
        MutableLiveData<ResponseWrapper<Void>> responseWrapper = new MutableLiveData<>();

        // set loading status
        responseWrapper.setValue(ResponseWrapper.loading());

        // delete user
        reqresService.deleteUser(userId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // set response value
                    responseWrapper.setValue(ResponseWrapper.success(response.body()));
                } else {
                    responseWrapper.setValue(ResponseWrapper.error(new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                responseWrapper.setValue(ResponseWrapper.error(t));
            }
        });

        return responseWrapper;
    }

    /* add new User */
    public MutableLiveData<ResponseWrapper<AddNewUserResponse>> addNewUser(String name, String job) {
        MutableLiveData<ResponseWrapper<AddNewUserResponse>> responseWrapper = new MutableLiveData<>();

        // set loading status
        responseWrapper.setValue(ResponseWrapper.loading());

        // add user
        reqresService.addNewUser(name, job).enqueue(new Callback<AddNewUserResponse>() {
            @Override
            public void onResponse(@NotNull Call<AddNewUserResponse> call, @NotNull Response<AddNewUserResponse> response) {
                if (response.isSuccessful()) {
                    // set response value
                    responseWrapper.setValue(ResponseWrapper.success(response.body()));
                } else {
                    responseWrapper.setValue(ResponseWrapper.error(new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(@NotNull Call<AddNewUserResponse> call, @NotNull Throwable t) {
                responseWrapper.setValue(ResponseWrapper.error(t));
            }
        });

        return responseWrapper;
    }

    /* add new User */
    public MutableLiveData<ResponseWrapper<UpdateUserResponse>> updateUser(int id, UpdateUserResponse user) {
        MutableLiveData<ResponseWrapper<UpdateUserResponse>> responseWrapper = new MutableLiveData<>();

        // set loading status
        responseWrapper.setValue(ResponseWrapper.loading());

        // add user
        reqresService.updateUser(id, user).enqueue(new Callback<UpdateUserResponse>() {
            @Override
            public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                if (response.isSuccessful()) {
                    // set response value
                    responseWrapper.setValue(ResponseWrapper.success(response.body()));
                } else {
                    responseWrapper.setValue(ResponseWrapper.error(new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(Call<UpdateUserResponse> call, Throwable t) {
                responseWrapper.setValue(ResponseWrapper.error(t));
            }
        });

        return responseWrapper;
    }
}
