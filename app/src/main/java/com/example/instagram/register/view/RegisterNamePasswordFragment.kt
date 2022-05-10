package com.example.instagram.register.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.common.base.DependencyInjector
import com.example.instagram.common.util.TxtWatcher
import com.example.instagram.databinding.FragmentRegisterNamePasswordBinding
import com.example.instagram.register.RegisterNameAndPassword
import com.example.instagram.register.presentation.RegisterNameAndPasswordPresenter

class RegisterNamePasswordFragment : Fragment(R.layout.fragment_register_name_password),
    RegisterNameAndPassword.view {
    companion object {
        const val KEY_EMAIL = "key_email"
    }
    private var fragamentAttachLiestener : FragamentAttachLiestener? = null //mudar para  tela Welcome-pass1
    private var binding: FragmentRegisterNamePasswordBinding? = null

    override lateinit var presenter: RegisterNameAndPassword.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterNamePasswordBinding.bind(view)

        val repository = DependencyInjector.registerEmailRepositoy()
        presenter = RegisterNameAndPasswordPresenter(this,repository)

        val email = arguments?.getString(KEY_EMAIL)//?: throw IllegalArgumentException("email not found")
       Log.i("teste",email.toString())
        binding?.let {
            with(it) {
                registerTxtLogin.setOnClickListener {
                    activity?.finish()
                }
                registerNameBtnNext.setOnClickListener {
                    presenter.create(
 //---------------------email ?:"",-----------------------------------------------
                        email?:"Email não encontrado",
                        registerEditName.text.toString(),
                        registerEditPassword.text.toString(),
                        registerEditComfirme.text.toString()
                    )
                }
                registerEditName.addTextChangedListener(watcher)
                registerEditPassword.addTextChangedListener(watcher)
                registerEditComfirme.addTextChangedListener(watcher)

                registerEditName.addTextChangedListener(TxtWatcher{
                    displayNamelFailure(null)
                })
                registerEditPassword.addTextChangedListener(TxtWatcher{
                    displayPasswordFailure(null)
                })
                registerEditComfirme.addTextChangedListener(TxtWatcher{
                    displayPasswordFailure(null)
                })
            }
        }
    }
    override fun onAttach(context: Context) { // Welcome-pass2
        super.onAttach(context)
        if(context is FragamentAttachLiestener){
            fragamentAttachLiestener = context
        }
    } //para o passo 3 declarar esse metodo dentro da (interface FragamentAttachLiestener)

    override fun showProgress(enable: Boolean) {
        binding?.registerNameBtnNext?.showProgress(enable)
    }
    override fun displayNamelFailure(nameError: Int?) {
        binding?.registerEditNameInput?.error = nameError?.let { getString(it) }
    }
    override fun displayPasswordFailure(passError: Int?) {
        binding?.registerEditPasswordInput?.error = passError?.let { getString(it) }
    }
    override fun onCreateFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    override fun onCreateSuccess(name: String) {
       fragamentAttachLiestener?.goToWelcomeScreen(name)// Welcome-pass 4
    }
    private val watcher = TxtWatcher{
     binding?.registerNameBtnNext?.isEnabled = binding?.registerEditName?.text.toString().isNotEmpty()
             && binding?.registerEditPassword?.text.toString().isNotEmpty()
             && binding?.registerEditComfirme?.text.toString().isNotEmpty()
    }
    override fun onDestroy() {
        binding = null
        presenter.onDestroy()
        super.onDestroy()
    }
}