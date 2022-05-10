package com.example.instagram.register

import androidx.annotation.StringRes
import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView

interface RegisterNameAndPassword {
    interface Presenter : BasePresenter {
        fun create( email: String,name: String, password: String, confirme : String)
    }
    interface view : BaseView<Presenter> {
        //criar caso de usos

        fun showProgress(enable : Boolean)
        fun displayNamelFailure(@StringRes nameError : Int?)
        fun displayPasswordFailure(@StringRes passError : Int?)
        fun onCreateFailure(message : String)
        fun onCreateSuccess(name: String)
    }
}