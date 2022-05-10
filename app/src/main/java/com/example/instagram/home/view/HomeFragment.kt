package com.example.instagram.home.view

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.common.base.BaseFragment
import com.example.instagram.common.base.DependencyInjector
import com.example.instagram.common.model.Post
import com.example.instagram.databinding.FragmentHomeBinding
import com.example.instagram.home.Home
import com.example.instagram.home.presenter.HomePresenter

class HomeFragment : BaseFragment<FragmentHomeBinding, Home.Presenter>(
    R.layout.fragment_home,
    FragmentHomeBinding::bind
), Home.View {
    override lateinit var presenter: Home.Presenter
    private val adapter = FeedAdapter()

    override fun setupViews() {
        binding?.homeRv?.layoutManager = LinearLayoutManager(requireContext())
        binding?.homeRv?.adapter = adapter
        presenter.fetchFeed()
    }

    override fun setupPresenter() {
       presenter = HomePresenter(this,DependencyInjector.homeRepository())
    }

    override fun getMenu() = R.menu.menu_profile

    override fun showProgress(enabled: Boolean) {
     binding?.HomeProgress?.visibility = if(enabled) View.VISIBLE else View.GONE
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun displayEmpytPost() {
        binding?.homeTxtEmpyt?.visibility = View.VISIBLE
        binding?.homeRv?.visibility = View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun displayFullPost(post: List<Post>) {
        binding?.homeTxtEmpyt?.visibility = View.GONE
        binding?.homeRv?.visibility = View.VISIBLE
        adapter.items = post
        adapter.notifyDataSetChanged()
    }
}
