package com.example.instagram.login

import androidx.annotation.StringRes
import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView

interface Login {
//camada de presenter
    interface Presenter : BasePresenter{
    fun login(email : String, password : String)
    }

    // camada de view
    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayEmailFailure(@StringRes emailError: Int?)
        fun displayPasswordFailure(@StringRes passwordError: Int?)
        fun onUserAuthenticated()
        fun onUserUnauthoried(message : String)
    }
}