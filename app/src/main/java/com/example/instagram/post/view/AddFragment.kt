package com.example.instagram.post.view

import  android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.instagram.R
import com.example.instagram.add.view.AddActivity
import com.example.instagram.databinding.FragmentAddBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class AddFragment : Fragment(R.layout.fragment_add) {

    private var binding: FragmentAddBinding? = null
    private var addListener: AddListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddBinding.bind(view)

        if (savedInstanceState == null)
            setupViews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("takePhotoKey"){ requestKey, bundle ->
         val uri  = bundle.getParcelable<Uri>("uri")
            uri.let {
                val intent = Intent(requireContext(), AddActivity::class.java)
                intent.putExtra("photoUri",uri)
                addActivityResult.launch(intent)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AddListener){
            addListener = context
        }
    }

    fun setupViews() {
        val tablayout = binding?.addTab
        val viewpager = binding?.addViewpager
        val adapater = AddViewPagerAdapter(requireActivity())
        viewpager?.adapter = adapater

        if (tablayout != null && viewpager != null) {
            tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab?.text == getString(adapater.tabs[0])) {
                        startCamera()
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })
            TabLayoutMediator(tablayout, viewpager) { tab, position ->
                tab.text = getString(adapater.tabs[position])
            }.attach()
        }
        if (allPermissionGranted()) {
            startCamera()
        } else {
            getPermission.launch(REQUIRED_PERMISSION)
        }
    }

    private fun startCamera() {
        setFragmentResult("camerakey", bundleOf("startCamera" to true))

    }
    private val addActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode== Activity.RESULT_OK){
          addListener?.onPostCreated()
        }
    }

    private val getPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { granted ->
            if (allPermissionGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    requireContext(),
                    R.string.permission_camera_denied,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun allPermissionGranted() =
        ContextCompat.checkSelfPermission(requireContext(), REQUIRED_PERMISSION[0]) == PackageManager.PERMISSION_GRANTED
                &&   ContextCompat.checkSelfPermission(requireContext(), REQUIRED_PERMISSION[1]) == PackageManager.PERMISSION_GRANTED

    interface AddListener{
      fun  onPostCreated()
    }

    companion object {
        private val REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}