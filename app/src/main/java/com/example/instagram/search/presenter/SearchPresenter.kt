package com.example.instagram.search.presenter

import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Database
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth
import com.example.instagram.profile.Profile
import com.example.instagram.profile.data.ProfileRepository
import com.example.instagram.search.Search
import com.example.instagram.search.data.SearchRepository

class SearchPresenter(
    private var view: Search.View?,
    private val repository: SearchRepository
) : Search.Presenter {

    override fun fetchUsers(name: String) {
        view?.showProgress(true)
         repository.fetchUsers(name,object : RequestCallback<List<UserAuth>> {
             override fun onSuccess(data: List<UserAuth>) {
                 if (data.isEmpty()) {
                     view?.displayEmptyUsers()
                 } else {
                     view?.displayFullUsers(data)
                 }
             }

             override fun onFailure(message: String) {
                 view?.displayEmptyUsers()
             }

             override fun onComplete() {
                 view?.showProgress(false)
             }
         })
     }

    override fun onDestroy() {
        view = null
    }
}