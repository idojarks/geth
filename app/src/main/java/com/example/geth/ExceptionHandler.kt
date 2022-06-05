package com.example.geth

import androidx.lifecycle.LiveData

class ExceptionHandler {
    companion object {
        fun onCatchException(e:Throwable) {
            e.message?.let {
                println(it)
            }
        }
    }
}