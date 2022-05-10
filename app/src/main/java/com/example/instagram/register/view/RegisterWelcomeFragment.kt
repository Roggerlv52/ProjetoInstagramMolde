package com.example.instagram.register.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.common.base.DependencyInjector
import com.example.instagram.common.util.TxtWatcher
import com.example.instagram.databinding.FragmentRegisterNamePasswordBinding
import com.example.instagram.databinding.FragmentRegisterWelcomeBinding
import com.example.instagram.register.RegisterNameAndPassword
import com.example.instagram.register.presentation.RegisterNameAndPasswordPresenter

// Welcome-pass8 declarar o layout aqui abaixo
class RegisterWelcomeFragment : Fragment(
    R.layout.fragment_register_welcome) {
    companion object {
        const val KEY_NAME = "key_name"
    }

    private var fragamentAttachLiestener: FragamentAttachLiestener? = null
    private var binding: FragmentRegisterWelcomeBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterWelcomeBinding.bind(view)

        val name = arguments?.getString(KEY_NAME)?: throw IllegalArgumentException("name not found")

        binding?.let {
            with(it) {
                registerBtnNext.isEnabled = true
                registerTxtWelcome.text = getString(R.string.welcome_to_instagram,name)
                registerBtnNext.setOnClickListener {
                    fragamentAttachLiestener?.goToPhotoSreen()
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

    override fun onDestroy() {
        binding = null

        super.onDestroy()
    }
}