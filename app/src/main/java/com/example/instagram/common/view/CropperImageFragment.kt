package com.example.instagram.common.view

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.instagram.R
import com.example.instagram.databinding.FragmentImageCropperBinding
import java.io.File

class CropperImageFragment : Fragment(R.layout.fragment_image_cropper) {
    private var binding : FragmentImageCropperBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentImageCropperBinding.bind(view)

        val  uri = arguments?.getParcelable<Uri>(KEY_URI)
        binding?.let {
            with(it){
                crooperContainer.setAspectRatio(1,1)
                crooperContainer.setFixedAspectRatio(true)
                crooperContainer.setImageUriAsync(uri)

                cropperBtnCancel.setOnClickListener {
                    parentFragmentManager.popBackStack()
                }
                crooperContainer.setOnCropImageCompleteListener{view , result ->
                    Log.i("Teste","nova image${result.uri}")
                    setFragmentResult("cropKey", bundleOf(KEY_URI to result.uri))
                    parentFragmentManager.popBackStack()
                }
                cropperBtnSave.setOnClickListener {
                    val dir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    if(dir != null){
                        val uriToSave = Uri.fromFile(
                            File(dir.path,
                        System.currentTimeMillis().toString()+".jpeg")
                        )
                        crooperContainer.saveCroppedImageAsync(uriToSave)
                    }
                }
            }
        }
    }
    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
    companion object{
        const val KEY_URI ="Key_uri"
    }
}