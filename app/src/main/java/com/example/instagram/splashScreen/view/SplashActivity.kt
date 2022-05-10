package com.example.instagram.splashScreen.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.instagram.common.base.DependencyInjector
import com.example.instagram.common.extension.animationEnd
import com.example.instagram.databinding.ActivitySplashBinding
import com.example.instagram.login.view.LoginActivity
import com.example.instagram.main.view.MainActivity
import com.example.instagram.splashScreen.Splash
import com.example.instagram.splashScreen.presentation.SplashPresenter


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(),Splash.View {

    private lateinit var  binding : ActivitySplashBinding
    override  lateinit var presenter: Splash.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = DependencyInjector.splashRepository()
        presenter = SplashPresenter(this,repository)


        binding.splashImg.animate().apply {
            setListener(animationEnd {
                presenter.authenticated()
            })
          duration = 1000
            alpha(1.0f)
            start()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun goToMainScreen() {
        binding.splashImg.animate().apply {
            setListener(animationEnd {
                val intent = Intent(baseContext,MainActivity::class.java)
                //para tirar ativvidade da frente
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            })
            duration = 1000
            startDelay = 1000
            alpha(0.0f)
            start()
        }

    }

    override fun goToLoginScreen() {
        binding.splashImg.animate().apply {
            setListener(animationEnd {
                val intent = Intent(baseContext, LoginActivity::class.java)
                //para tirar ativvidade da frente
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            })
            duration = 1000
            startDelay = 1000
            alpha(0.0f)
            start()
        }
    }
}