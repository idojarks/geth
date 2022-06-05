package com.example.geth

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap
import com.fasterxml.jackson.core.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.internal.closeQuietly
import org.json.JSONObject
import org.json.JSONTokener
import org.web3j.protocol.http.HttpService
import java.io.File
import java.io.IOException
import kotlin.io.path.Path
import kotlin.io.path.absolute
import kotlin.io.path.absolutePathString

class HttpClient {
    companion object {
        val instance = HttpService.getOkHttpClientBuilder()
            .build()

        private fun getAsync(url: String, callback: (Result<ResponseBody>) -> Unit) {
            val request = Request.Builder()
                .url(url)
                .get()
                .build()

            instance.newCall(request)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback(Result.failure(e))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) {
                            response.body?.let {
                                callback(Result.success(it))
                            }
                        } else {
                            callback(Result.failure(IllegalAccessException()))
                        }
                    }
                })
        }

        private fun get(url: String): ResponseBody? {
            return runCatching {
                val request = Request.Builder()
                    .url(url)
                    .get()
                    .build()

                instance.newCall(request)
                    .execute()
                    .takeIf {
                        it.isSuccessful
                    }?.body
            }.onFailure {
                ExceptionHandler.onCatchException(it)
            }
                .onSuccess {

                }
                .getOrNull()
        }

        fun getJson(url: String): JSONObject? {
            val responseBody = get(url)
                ?: return null

            val json = JSONObject(responseBody.string())

            responseBody.close()

            return json
        }

        fun getJsonAsync(url: String, callback: (JSONObject) -> Unit) {
            getAsync(url) { result ->
                result.onFailure {

                }
                    .onSuccess {
                        val json = JSONObject(it.string())
                        it.close()
                        callback(json)
                    }
            }
        }

        fun downloadImage(
            uri: String,
            filename: String,
        ): String {
            val responseBody = get(uri)
                ?: return ""

            val contentType = responseBody.contentType()
                ?: return ""

            if (contentType.type != "image") {
                return ""
            }

            val pathname = Path("${filename}.${contentType.subtype}").absolutePathString()

            File(pathname).writeBytes(responseBody.bytes())

            responseBody.close()

            return pathname
        }
    }
}