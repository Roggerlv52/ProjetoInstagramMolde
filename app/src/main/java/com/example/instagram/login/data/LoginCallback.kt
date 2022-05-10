package com.example.instagram.login.data

import com.example.instagram.common.model.UserAuth

interface LoginCallback {
    fun onSuccess(userAuth: UserAuth) // caso de sucesso
    fun onFailure(message : String) // para messagem de erro do cervidor
    fun onComplete()
}