package com.example.instagram.search

import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView
import com.example.instagram.common.model.UserAuth

interface Search {
    interface Presenter:BasePresenter{
        fun fetchUsers(name : String)
    }
    interface View:BaseView<Presenter>{
     fun showProgress(enabled:Boolean)
     fun displayFullUsers(users:List<UserAuth>)
     fun displayEmptyUsers()
    }
}