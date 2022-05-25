package com.example.instagram.register.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.common.extension.hidekyboard
import com.example.instagram.common.extension.replaceFragment
import com.example.instagram.common.view.CropperImageFragment
import com.example.instagram.common.view.CropperImageFragment.Companion.KEY_URI
import com.example.instagram.databinding.ActivityRegisterBinding
import com.example.instagram.main.view.MainActivity
import com.example.instagram.register.view.RegisterNamePasswordFragment.Companion.KEY_EMAIL
import com.example.instagram.register.view.RegisterWelcomeFragment.Companion.KEY_NAME
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws

// Welcome-pass5 Necessario implementar o metados
class RegisterActivity : AppCompatActivity(), FragamentAttachLiestener {
    private lateinit var binding: ActivityRegisterBinding

    private lateinit var currenPhoto : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = RegisterEmailFragment()    //apply  para executar metados
        replaceFragment(fragment)
    }

    override fun goToNameAndpasswordScreen(email: String) {
        val fragment = RegisterNamePasswordFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_EMAIL, email)
            }
        }
        replaceFragment(fragment)
/*
        val args = Bundle()
        args.putString( KEY_EMAIL,email)
        val fragment = RegisterNamePasswordFragment()
         fragment.arguments = args
        replaceFragment(fragment)
*/
    }

    // Welcome-pass5
    override fun goToWelcomeScreen(name: String) {
        val fragment = RegisterWelcomeFragment().apply { // Welcome-pass6
            arguments = Bundle().apply {
                putString(KEY_NAME, name) // Welcome-pass7 criar chave valor na tela  RegisterWelcomeFragment
            }
        }
        replaceFragment(fragment)
    }

    override fun goToPhotoSreen() {
        val fragment = RegisterPhotoFragment()
        replaceFragment(fragment)
    }

    override fun goToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
//    open galeria
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
    uri?.let {
        openImageCropper(it) }

        }

    override fun goToGalleryScreen() {
        getContent.launch("image/*")
    }
//open camera
    private val getCamera = registerForActivityResult(ActivityResultContracts.TakePicture()){ saved ->
       if(saved){
         openImageCropper(currenPhoto)
       }
    }
    override fun goToCameraSreen() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {

            val photoFile: File? = try {
                createImageFile()
            } catch (e: IOException) {
                Log.e("RegisterActivity", e.message, e)
                null
            }
            photoFile?.also {
                val photoUri = FileProvider.getUriForFile(this,"com.example.instagram.fileprovider",it)
                currenPhoto = photoUri
                getCamera.launch(photoUri)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("jPEG_${timestamp}_","jpeg",dir)
    }

    private fun  replaceFragment(fragment: Fragment) {
        if (supportFragmentManager.findFragmentById(R.id.register_fragment)== null){
            supportFragmentManager.beginTransaction().apply {
                add(R.id.register_fragment,fragment)
                commit()
            }
        }else{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.register_fragment,fragment)
                addToBackStack(null)
                commit()
            }
        }
            hidekyboard()
    }
    private fun openImageCropper(uri: Uri){
        val fragment = CropperImageFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_URI, uri)
            }
        }
        replaceFragment(fragment)
    }
}