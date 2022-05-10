package com.example.instagram.register.presentation

import com.example.instagram.R
import com.example.instagram.register.RegisterNameAndPassword
import com.example.instagram.register.data.RegisterCallback
import com.example.instagram.register.data.RegisterRepository

class RegisterNameAndPasswordPresenter(
    private var view: RegisterNameAndPassword.view?,
    private val repository: RegisterRepository
): RegisterNameAndPassword.Presenter{

    override fun create(email: String, name: String, password: String, confirme: String) {

        val isNameValid = name.length >=3
        val isPasswordValid = password.length >= 8
        val isConfirmValid = password == confirme

        if (!isNameValid){
           view?.displayNamelFailure(R.string.invalid_name)
        }else{
         view?.displayNamelFailure(null)
        }

        if (!isConfirmValid){
           view?.displayPasswordFailure(R.string.password_not_equal)
        }else{
            if (!isPasswordValid){
                view?.displayPasswordFailure(R.string.invalid_password)
            }else{
                view?.displayPasswordFailure(null)
            }
        }

        if (isNameValid && isPasswordValid && isConfirmValid){
            view?.showProgress(true)

            repository.create( email,name,password, object : RegisterCallback{
                override fun onSuccess() {
                   view?.onCreateSuccess(name)
                }
                override fun onFailure(message: String) {
                   view?.onCreateFailure(message)
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