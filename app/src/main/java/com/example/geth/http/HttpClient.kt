package com.example.geth.http

import com.example.geth.ExceptionHandler
import okhttp3.*
import org.json.JSONObject
import org.web3j.protocol.http.HttpService
import java.io.File
import java.io.IOException
import kotlin.io.path.Path
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

        private fun get(url: String): Response? {
            return runCatching {
                val request = Request.Builder()
                    .url(url)
                    .get()
                    .build()

                instance.newCall(request)
                    .execute()
            }.onFailure {
                ExceptionHandler.onCatchException(it)
            }
                .getOrNull()
        }

        private fun getResponseBody(response: Response): ResponseBody? {
            return if (response.isSuccessful) {
                response.body
            } else {
                for (status in HttpStatus.values()) {
                    if (status.code == response.code) {
                        ExceptionHandler.onCatchException(Exception("http status ${status.code} : ${status.desc}"))
                    }
                }
                null
            }
        }

        fun getJson(url: String): JSONObject? {
            val response = get(url)
                ?: return null

            val body = getResponseBody(response)
                ?: return null

            val json = JSONObject(body.string())

            response.close()

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
            val response = get(uri)
                ?: return ""

            val body = getResponseBody(response)
                ?: return ""

            val contentType = body.contentType()
                ?: return ""

            if (contentType.type != "image") {
                return ""
            }

            val pathname = Path("${filename}.${contentType.subtype}").absolutePathString()

            File(pathname).writeBytes(body.bytes())

            response.close()

            return pathname
        }
    }
}