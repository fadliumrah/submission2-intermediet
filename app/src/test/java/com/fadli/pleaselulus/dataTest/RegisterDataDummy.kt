package com.fadli.pleaselulus.dataTest

import com.fadli.pleaselulus.data.RegisterUserResponse


fun successRegisterTest(): RegisterUserResponse {
    return RegisterUserResponse(
        error = false,
        message = "User Created"
    )
}
fun failedRegisterTest(): RegisterUserResponse {
    return RegisterUserResponse(
        error = true,
        message = "Email is already taken"

    )
}