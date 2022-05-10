package com.example.instagram.profile.view

import android.annotation.SuppressLint
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.common.base.BaseFragment
import com.example.instagram.common.base.DependencyInjector
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth
import com.example.instagram.databinding.FragmentProfileBinding
import com.example.instagram.profile.Profile
import com.example.instagram.profile.presenter.ProfilePresenter
import com.example.instagram.search.view.Search_Fragment.Companion.KEY_USER_ID
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment : BaseFragment<FragmentProfileBinding, Profile.Presenter>(
    R.layout.fragment_profile,
    FragmentProfileBinding::bind
), Profile.View ,BottomNavigationView.OnNavigationItemSelectedListener{
    private val adapter = PostAdapter()
    private var uuid :String? = null

    override lateinit var presenter: Profile.Presenter

    override fun setupPresenter() {
        uuid =  arguments?.getString(KEY_USER_ID)
          val repository = DependencyInjector.profileRepository()
          presenter = ProfilePresenter(this,repository)
    }

    override fun setupViews() {
        uuid =  arguments?.getString(KEY_USER_ID)
        binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.profileRv?.adapter = adapter
        binding?.profileNavTabs?.setOnNavigationItemSelectedListener (this)

        binding?.profileBtnEditProfile?.setOnClickListener{
           if(it.tag == true){
               binding?.profileBtnEditProfile?.text = getString(R.string.follow)
               binding?.profileBtnEditProfile?.tag = false
               presenter.seguirUser(uuid,false)
           }else if(it.tag == false){
               binding?.profileBtnEditProfile?.text = getString(R.string.unfollow)
               binding?.profileBtnEditProfile?.tag = true
               presenter.seguirUser(uuid,true)
           }
        }
        presenter.fetchUserProfile(uuid)
    }

    override fun showProgress(enabled: Boolean) {
        binding?.profileProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayUserProfile(user: Pair<UserAuth, Boolean?>) {
        val (userAuth,following) = user

        binding?.profileTxtPostsCount?.text = userAuth.posCount.toString()
        binding?.profileTxtFolewingCount?.text = userAuth.follwingCount.toString()
        binding?.profileTxtFollewersCount?.text = userAuth.followersCount.toString()
        binding?.profileTxtUserneme?.text = userAuth.name
        binding?.profileTxtBio?.text = "TODO"
        binding?.profileImgIcon?.setImageURI(userAuth.photoUri)
        binding?.profileBtnEditProfile?.text = when(following){
            null-> getString(R.string.edit_profile)
            true -> getString(R.string.unfollow)
            false -> getString(R.string.follow)
        }
        binding?.profileBtnEditProfile?.tag = following
         presenter.fetchUserPost(uuid)
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun displayEmpytPost() {
        binding?.profileTxtEmpyt?.visibility = View.VISIBLE
        binding?.profileRv?.visibility = View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun displayFullPost(post: List<Post>) {
        binding?.profileTxtEmpyt?.visibility = View.GONE
        binding?.profileRv?.visibility = View.VISIBLE
        adapter.items = post
        adapter.notifyDataSetChanged()
    }

    override fun getMenu(): Int {
        return R.menu.menu_profile
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
       when(item.itemId){
           R.id.menu_profile_grid->{
               binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(),3)
           }
           R.id.menu_profiel_list ->{
               binding?.profileRv?.layoutManager = LinearLayoutManager(requireContext())
           }
       }
        return true
    }
}





