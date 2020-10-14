package ru.raralux.itunesfs.utils

import okhttp3.ResponseBody
import org.json.JSONObject
import ru.raralux.itunesfs.service.response.ErrorResponse

class ErrorUtils {

    fun parseError(response: ResponseBody?): ErrorResponse? {
        return try {
            val jsonObject = JSONObject(response!!.string())
            val errorResponse = ErrorResponse()
            errorResponse.errorMessage = jsonObject.getString("errorMessage")
            return errorResponse
        } catch (e: Exception) {
            val errorResponse = ErrorResponse()
            errorResponse.errorMessage = "Something wrong happened"
            errorResponse
        }
    }
}