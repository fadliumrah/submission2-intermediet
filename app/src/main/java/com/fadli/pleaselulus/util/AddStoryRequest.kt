package com.fadli.pleaselulus.util

import com.fadli.pleaselulus.data.ErrorUserResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.net.URLConnection

fun createPartFromString(data: String): RequestBody{
    return data.toRequestBody("text/plain".toMediaType())
}

fun prepareFilePart(partName: String, file: File): MultipartBody.Part{
    val name = URLConnection.guessContentTypeFromName(file.name)

    val requestFile: RequestBody = file.asRequestBody(name.toMediaTypeOrNull())

    return MultipartBody.Part.createFormData(partName, file.name, requestFile)
}
fun convertErrorData(errorData: String): ErrorUserResponse {
    return try {
        val gson = Gson()
        gson.fromJson(errorData, ErrorUserResponse::class.java)
    }catch (e: Exception) {
        e.printStackTrace()
        ErrorUserResponse(true, null)
    }
}
