package com.example.instagram.register.view

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.instagram.R
import com.example.instagram.common.base.DependencyInjector
import com.example.instagram.common.view.CropperImageFragment
import com.example.instagram.common.view.CustomDialog
import com.example.instagram.databinding.FragmentRegisterPhotoBinding
import com.example.instagram.register.RegisterPhoto
import com.example.instagram.register.presentation.RegisterPhotoPresenter

class RegisterPhotoFragment : Fragment(R.layout.fragment_register_photo),RegisterPhoto.view {
    private var binding: FragmentRegisterPhotoBinding? = null
    private var fragamentAttachLiestener: FragamentAttachLiestener? = null

    override lateinit var presenter: RegisterPhoto.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("cropKey") { requestKey, bundle ->
            val uri = bundle.getParcelable<Uri>(CropperImageFragment.KEY_URI)
            onCropImageResult(uri)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterPhotoBinding.bind(view)
        val repository = DependencyInjector.registerEmailRepositoy()
        presenter = RegisterPhotoPresenter(this, repository)

        binding?.let {
            with(it) {
                regiterBtnJump.setOnClickListener {
                    fragamentAttachLiestener?.goToMainScreen()
                }
                registerBtnNext.isEnabled = true
                registerBtnNext.setOnClickListener {
                    openDialog()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragamentAttachLiestener) {
            fragamentAttachLiestener = context
        }
    }

    override fun showProgress(enable: Boolean) {
       binding?.registerBtnNext?.showProgress(enable)
    }

    override fun onUpdateFailure(message: String) {
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateSuccess() {
      fragamentAttachLiestener?.goToMainScreen()
    }

    private fun openDialog() {
        val customDialog = CustomDialog(requireContext())
        customDialog.addButton(R.string.photo, R.string.gallery) {
            when (it.id) {
                R.string.photo -> {
                    Log.i("Teste", "Foto")
                    fragamentAttachLiestener?.goToCameraSreen()
                }
                R.string.gallery -> {
                    fragamentAttachLiestener?.goToGalleryScreen()
                }
            }
        }
        customDialog.show()
    }

    private fun onCropImageResult(uri: Uri?) {
        if (uri != null) {
            val bitmap = if (Build.VERSION.SDK_INT >= 28) {
                val souce = ImageDecoder.createSource(requireContext().contentResolver, uri)
                ImageDecoder.decodeBitmap(souce)
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            }
            binding?.registerImgPrifile?.setImageBitmap(bitmap)
            presenter.updateUser(uri)
        }
    }

    override fun onDestroy() {
        binding = null
        presenter.onDestroy()
        super.onDestroy()
    }

}