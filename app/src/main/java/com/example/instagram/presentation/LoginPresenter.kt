package com.example.instagram.presentation

import android.util.Log
import android.util.Patterns
import com.example.instagram.R
import com.example.instagram.common.model.UserAuth
import com.example.instagram.login.Login
import com.example.instagram.login.data.LoginCallback
import com.example.instagram.login.data.LoginRepository

class LoginPresenter(
    private var view : Login.View?,
    private var repository : LoginRepository) : Login.Presenter{

    override fun login(email: String, password: String) {
        Log.e("Teste",email)
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.length >=8

        if (!isEmailValid){
           view?.displayEmailFailure(R.string.invalid_email)
        }else{
         view?.displayEmailFailure(null)
        }
        if (!isPasswordValid){
         view?.displayPasswordFailure(R.string.invalid_password)
        }else{
            view?.displayPasswordFailure(null)
        }
        if (isEmailValid && isPasswordValid){
            view?.showProgress(true)
            repository.logon(email,password, object : LoginCallback{

                override fun onSuccess(userAuth: UserAuth) {
                   view?.onUserAuthenticated()
                }
                override fun onFailure(message : String) {
                    view?.onUserUnauthoried(message)
                }
                override fun onComplete() {
                  view?.showProgress(false)
                }

            })
        }
    }
    override fun onDestroy() {
         view = null
    }

}