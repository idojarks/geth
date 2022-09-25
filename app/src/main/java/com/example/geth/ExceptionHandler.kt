package com.example.geth

class ExceptionHandler {
    companion object {
        var lastExceptionMessage: String = ""

        fun onCatchException(e: Throwable) {
            e.message?.let {
                println(it)
                lastExceptionMessage = it
            }
        }
    }
}