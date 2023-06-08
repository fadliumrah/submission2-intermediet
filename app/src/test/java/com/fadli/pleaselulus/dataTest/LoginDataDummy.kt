package com.fadli.pleaselulus.dataTest

import com.fadli.pleaselulus.data.LoginUserResult
import com.fadli.pleaselulus.data.LoginStoriesResponse


fun successLoginTest(): LoginStoriesResponse {
    val loginUserResult = LoginUserResult(
        userId = "user-y4gWQzEtveVTg6Up",
        name = "farhan",
        token = "token"
    )
    return LoginStoriesResponse(
        error = false,
        message = "success",
        loginUserResult = loginUserResult
    )
}

fun failedLoginTest(): LoginStoriesResponse{
    return LoginStoriesResponse(
        error = true,
        message = "Invalid password"
    )
}