package com.example.geth

import android.content.Context
import android.widget.Toast
import java.io.File

class SavedAccount {
    private val filename = "accounts"

    fun load(
        context: Context
    ): List<String> {
        val file = File(context.filesDir, filename)

        return if (file.exists()) {
            val accounts = mutableListOf<String>()

            context
                .openFileInput(filename)
                .bufferedReader()
                .useLines {
                    it.forEach {
                        accounts.add(it)
                    }
                }

            accounts
        }
        else {
            emptyList()
        }
    }

    fun delete(
        context: Context,
        account: String
    ) {
        val file = File(context.filesDir, filename)

        if (!file.exists()) {
            return
        }

        val accounts = mutableListOf<String>()

        context
            .openFileInput(filename)
            .bufferedReader()
            .useLines {
                it.forEach {
                    if (it != account) {
                        accounts.add(it)
                    }
                }
            }

        file.delete()

        context
            .openFileOutput(filename, Context.MODE_PRIVATE)
            .use { stream ->
                accounts.forEach {
                    stream.write(it.toByteArray())
                    stream.writer().appendLine()
                }
            }
    }

    fun save(
        context: Context,
        account: String,
        callback: () -> Unit
    ) {
        var alreadyExists = false
        val file = File(context.filesDir, filename)

        if (file.exists()) {
            context
                .openFileInput(filename)
                .bufferedReader()
                .useLines {
                    val result = it
                        .find { line ->
                            line == account
                        }

                    alreadyExists = !result.isNullOrEmpty()
                }
        }

        if (alreadyExists) {
            Toast.makeText(
                context,
                "account exists",
                Toast.LENGTH_SHORT
            )
                .show()
            return
        }

        context
            .openFileOutput(filename, Context.MODE_APPEND)
            .use {
                val s = account.plus("\n")
                it.write(s.toByteArray())
                callback()
            }
    }
}

