package com.example.instagram.common.base

interface RequestCallback<T> {
    fun onSuccess(data: T)
    fun onFailure(message:String)
    fun onComplete()
}