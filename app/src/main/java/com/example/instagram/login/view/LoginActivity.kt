package com.example.instagram.login.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.common.base.DependencyInjector
import com.example.instagram.common.util.TxtWatcher
import com.example.instagram.databinding.ActivityLoginBinding
import com.example.instagram.login.Login
import com.example.instagram.main.view.MainActivity
import com.example.instagram.presentation.LoginPresenter
import com.example.instagram.register.view.RegisterActivity

class LoginActivity : AppCompatActivity(),Login.View {
    // private lateinit var variavel que não aceita null
    private lateinit var binding: ActivityLoginBinding
    override lateinit var presenter : Login.Presenter //referencia para uma interface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = LoginPresenter(this,DependencyInjector.loginRepository())

        with(binding) {

            loginEditEmail.addTextChangedListener(Watcher)
            loginEditEmail.addTextChangedListener(TxtWatcher{
              displayEmailFailure(null)
            })
            loginEditPassword.addTextChangedListener(Watcher)
            loginEditPassword.addTextChangedListener(TxtWatcher{
                displayPasswordFailure(null)
            })
            loginBtnEnter.setOnClickListener {
                // Chamar o PRESENTER
                presenter.login(loginEditEmail.text.toString(),loginEditPassword.text.toString())

            }
            loginTxtRegister.setOnClickListener {
                goToRegisterScreen()
            }
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
    private val Watcher = TxtWatcher{
        binding.loginBtnEnter.isEnabled = binding.loginEditEmail.text.toString().isNotEmpty()
                && binding.loginEditPassword.text.toString().isNotEmpty()
    }
    private fun  goToRegisterScreen(){
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun showProgress(enabled: Boolean) {
       binding.loginBtnEnter.showProgress(enabled)
    }
    override fun displayEmailFailure(emailError: Int?) {
        binding.loginEditEmailInput.error = emailError?.let { getString(it) }
    }
    override fun displayPasswordFailure(passwordError: Int?) {
        binding.loginEditPasswordInput.error = passwordError?.let { getString(it) }
    }

    override fun onUserAuthenticated() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)

    }
    override fun onUserUnauthoried(message:String) = //  Mostra um alerta
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()

    //Objeto para input de texto para alterar o texto
    /*
    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            //       findViewById<Button>(R.id.login_btn_enter).isEnabled = s.toString().isNotEmpty()   NOVA ALTERAÇÃO
            binding.loginBtnEnter.isEnabled = s.toString().isNotEmpty()
        }
        override fun afterTextChanged(s: Editable?) {
        }
    }
     */
}