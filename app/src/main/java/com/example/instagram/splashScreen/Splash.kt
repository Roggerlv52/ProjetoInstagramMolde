package com.example.instagram.splashScreen

import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView

interface Splash {
    interface Presenter : BasePresenter{
       fun authenticated()
    }
    interface View : BaseView<Presenter>{
        fun goToMainScreen()
        fun goToLoginScreen()
    }
}