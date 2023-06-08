package com.fadli.pleaselulus.dataTest


import com.fadli.pleaselulus.data.UploadStoriesResponse

fun successAddStoriesTest(): UploadStoriesResponse {
    return UploadStoriesResponse(
        error = false,
        message = "Story created successfully"
    )
}

fun failedAddStoriesTest(): UploadStoriesResponse {
    return UploadStoriesResponse(
        error = false,
        message = "photo should be Readable"
    )
}