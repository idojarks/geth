package com.example.geth.service.http

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

        private fun getAsync(url: String, callback: (Result<Response>) -> Unit) {
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
                        callback(Result.success(response))
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
                findHttpStatus(response.code)?.let {
                    ExceptionHandler.onCatchException(Exception("http status ${it.code} : ${it.desc}"))
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
                    ExceptionHandler.onCatchException(it)
                }
                    .onSuccess { response ->
                        getResponseBody(response)?.let { body ->
                            val json = JSONObject(body.string())
                            callback(json)
                        }

                        response.close()
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

        fun getTokenImageUrl(
            uri: String,
        ): String? {
            val metadata = "$uri/metadata.json"

            val response = get(metadata)
                ?: return null

            val body = getResponseBody(response)
                ?: return null

            val json = JSONObject(body.string())
            val imageUri = json.get("image")
                .toString()
                .replace("ipfs://", "")

            val lastSlashIndex = uri.lastIndexOf('/')
            val baseUrl = uri.slice(0..lastSlashIndex)

            return "$baseUrl$imageUri"
        }
    }
}