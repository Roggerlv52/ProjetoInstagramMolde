package com.example.instagram.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.common.base.DependencyInjector
import com.example.instagram.common.util.TxtWatcher
import com.example.instagram.databinding.FragmentRegisterEmailBinding
import com.example.instagram.register.RegisterEmail
import com.example.instagram.register.presentation.RegisterEmailPresenter

class RegisterEmailFragment : Fragment(R.layout.fragment_register_email),
    RegisterEmail.View {
    private var binding: FragmentRegisterEmailBinding? = null
    private var fragamentAttachLiestener : FragamentAttachLiestener? = null

    override lateinit var presenter: RegisterEmail.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterEmailBinding.bind(view)

        val repositer = DependencyInjector.registerEmailRepositoy()
         presenter = RegisterEmailPresenter(this,repositer)

        //buscar as referencias em baixo
        binding?.let {
            //matar a atividade para voltar para tela de Login
            with(it){
              registerTxtLogin.setOnClickListener {
                  activity?.finish()
              }
                registerBtnNext.setOnClickListener {
                    presenter.create(
                        registerEditEmail.text.toString()
                    )
                }
                registerEditEmail.addTextChangedListener(watcher)
                registerEditEmail.addTextChangedListener(TxtWatcher{
                    displayEmailFailure(null)
                })
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragamentAttachLiestener){
            fragamentAttachLiestener = context
        }
    }
    override fun onDestroy() {
        binding = null
        fragamentAttachLiestener = null
        presenter.onDestroy()
        super.onDestroy()
    }
    private val watcher = TxtWatcher{
        binding?.registerBtnNext?.isEnabled = binding?.registerEditEmail?.text.toString().isNotEmpty()
    }
    override fun showProgress(enable: Boolean) {
        binding?.registerBtnNext?.showProgress(enable)
    }
    override fun displayEmailFailure(emailError: Int?) {
       binding?.registerEditEmailInput?.error = emailError?.let { getString(it) }
    }
    override fun onEmailFalure(message: String) {
       binding?.registerEditEmailInput?.error = message
    }
    override fun goToNameAndPasswordScreen(email: String) {
      //ir para proxima tela
        fragamentAttachLiestener?.goToNameAndpasswordScreen(email)
    }
}

