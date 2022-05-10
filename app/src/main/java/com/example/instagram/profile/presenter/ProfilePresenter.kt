package com.example.instagram.profile.presenter

import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth
import com.example.instagram.profile.Profile
import com.example.instagram.profile.data.ProfileRepository

class ProfilePresenter(
    private var view: Profile.View?,
    private val repository: ProfileRepository
) : Profile.Presenter {
    override fun clear() {
        repository.clearcache()
    }



    override fun fetchUserProfile(uuid: String?) {
     view?.showProgress(true)

        repository.fetchUserProfile(uuid,object : RequestCallback<Pair<UserAuth,Boolean?>> {
            override fun onSuccess(data:Pair< UserAuth,Boolean?>) {
                view?.displayUserProfile(data)
            }

            override fun onFailure(message: String) {
                view?.displayRequestFailure(message)
            }

            override fun onComplete() {

            }
        })

    }

    override fun fetchUserPost(uuid: String?) {
         repository.fetchUserPost(uuid,object : RequestCallback<List<Post>> {
             override fun onSuccess(data: List<Post>) {
                 if (data.isEmpty()) {
                     view?.displayEmpytPost()
                 } else {
                     view?.displayFullPost(data)
                 }
             }

             override fun onFailure(message: String) {
                 view?.displayRequestFailure(message)
             }

             override fun onComplete() {
                 view?.showProgress(false)
             }
         })
     }

    override fun seguirUser(uuid: String?, follow: Boolean) {
        repository.followUser(uuid,follow,object :RequestCallback<Boolean>{
            override fun onSuccess(data: Boolean) {}

            override fun onFailure(message: String) {}

            override fun onComplete() {}
        })
    }

    override fun onDestroy() {
        view = null
    }
}