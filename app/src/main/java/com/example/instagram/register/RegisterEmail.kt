package com.example.instagram.register

import androidx.annotation.StringRes
import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView

interface RegisterEmail {

    interface Presenter : BasePresenter{

         fun create(email: String)
    }
    interface View : BaseView<Presenter>{//criar caso de usos
        fun showProgress(enable : Boolean)

        fun displayEmailFailure(@StringRes emailError : Int?)

        fun onEmailFalure(message : String)

        fun goToNameAndPasswordScreen(email: String)

    }
}