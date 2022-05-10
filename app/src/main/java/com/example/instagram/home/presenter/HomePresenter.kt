package com.example.instagram.home.presenter

import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Post
import com.example.instagram.home.Home
import com.example.instagram.home.data.HomeRepository

class HomePresenter(
    private var view : Home.View?,
    private val repository: HomeRepository
):Home.Presenter {

    override fun clear() {
        repository.clearCacher()
    }

    override fun fetchFeed() {
        view?.showProgress(true)
        repository.fetchfeed(object : RequestCallback<List<Post>> {
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

    override fun onDestroy() {
        view = null
    }
}