package com.fadli.pleaselulus.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fadli.pleaselulus.repository.RegisterR
import com.fadli.pleaselulus.data.RegisterUserResponse
import com.fadli.pleaselulus.data.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RegisterV @Inject constructor(private val registerR: RegisterR) : ViewModel(){

    fun userRegister(name: String, email: String, password: String): LiveData<ResultData<RegisterUserResponse>> =
        registerR.userRegister(name, email, password)
}