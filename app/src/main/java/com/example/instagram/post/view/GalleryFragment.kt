package com.example.instagram.post.view

import android.annotation.SuppressLint
import android.net.Uri
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import com.example.instagram.R
import com.example.instagram.common.base.BaseFragment
import com.example.instagram.common.base.DependencyInjector
import com.example.instagram.post.Post
import com.example.instagram.databinding.FragmentGalleryBinding
import com.example.instagram.post.presenter.PostPresenter

class GalleryFragment :BaseFragment<FragmentGalleryBinding,Post.Presenter>(
R.layout.fragment_gallery,
    FragmentGalleryBinding::bind
),Post.View {
    override lateinit var presenter: Post.Presenter

    private  val adapter = PictureAdapter { uri ->

        binding?.galleryImgSelected?.setImageURI(uri)
        binding?.galeriaNestd?.smoothScrollTo(0,0)
        presenter.selectUri(uri)
    }

    override fun setupPresenter() {
    presenter = PostPresenter(this,DependencyInjector.postRepository(requireContext()))
    }

    override fun getMenu(): Int = R.menu.menu_send

    override fun setupViews() {
        binding?.galeriaRv?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.galeriaRv?.adapter = adapter

        presenter.fetchPictures()
    }
    override fun showProgress(enabled: Boolean) {
        binding?.galeriaProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun displayFullPictures(post: List<Uri>) {
        binding?.galeriaTxt?.visibility = View.GONE
        binding?.galeriaRv?.visibility = View.VISIBLE
        adapter.items = post
        adapter.notifyDataSetChanged()
        binding?.galleryImgSelected?.setImageURI(post.first())
        binding?.galeriaNestd?.smoothScrollTo(0,0)
        presenter.selectUri(post.first())
    }

    override fun displayEmpytPictures() {
        binding?.galeriaTxt?.visibility = View.VISIBLE
        binding?.galeriaRv?.visibility = View.GONE
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_send ->{
                setFragmentResult("takePhotoKey", bundleOf("uri" to presenter.getSelectUri()))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}


