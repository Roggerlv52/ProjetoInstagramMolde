package com.example.instagram.main.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Contacts.SettingsColumns.KEY
import android.util.Log
import android.view.MenuItem
import android.view.WindowInsetsController
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.post.view.AddFragment
import com.example.instagram.common.extension.replaceFragment
import com.example.instagram.databinding.ActivityMainBinding
import com.example.instagram.home.view.HomeFragment
import com.example.instagram.profile.view.ProfileFragment
import com.example.instagram.search.view.Search_Fragment
import com.example.instagram.search.view.Search_Fragment.Companion.KEY_USER_ID
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    AddFragment.AddListener,Search_Fragment.SearchListner {
    private lateinit var binding: ActivityMainBinding
    private lateinit var homeFragment: HomeFragment
    private lateinit var searchFragment: Fragment
    private lateinit var addFragment: Fragment
    private lateinit var profileFragment: ProfileFragment
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //para  mudar os incones da statubar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
// esse broclo so ira funcionar se a versão do sdk for mair ou igual Build.VERSION_CODES.R
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
            //teg para mudar tema da statusbar
            window.statusBarColor = ContextCompat.getColor(this, R.color.gray)
        }

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_insta_camera)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""//straing vazia para deichar o icone sempre do lado esquerdo

        homeFragment = HomeFragment()
        searchFragment = Search_Fragment()
        addFragment = AddFragment()
        profileFragment = ProfileFragment()
        //      currentFragment = homeFragment

        binding.mainBottomBav.setOnNavigationItemSelectedListener(this)
        binding.mainBottomBav.selectedItemId = R.id.menu_bottom_home
    }

    private fun setScrollToolbarEnabled(enabled: Boolean) {
        val params = binding.mainToolbar.layoutParams as AppBarLayout.LayoutParams
        val coordinatortParams = binding.mainAppbar.layoutParams as CoordinatorLayout.LayoutParams

        if (enabled) {
            params.scrollFlags =
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            coordinatortParams.behavior = AppBarLayout.Behavior()
            binding.mainAppbar.layoutParams = coordinatortParams
        } else {
            params.scrollFlags = 0
            coordinatortParams.behavior = null
        }
        binding.mainAppbar.layoutParams = coordinatortParams
    }

    override fun goToProfile(uuid: String) {
        val fragment = ProfileFragment().apply {
            arguments = Bundle().apply {
                // putString(ProfileFragment.KEY_USER_ID,uuid)
                    putString(KEY_USER_ID,uuid)
            }
        }
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment,fragment,fragment.javaClass.simpleName + "detail")
            addToBackStack(null)
            commit()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var scrollToolbarEnabled = false

        when (item.itemId) {
            R.id.menu_bottom_home -> {
                if (currentFragment == homeFragment) return false
                currentFragment = homeFragment

            }
            R.id.menu_bottom_search -> {
                if (currentFragment == searchFragment) return false
                currentFragment = searchFragment
                scrollToolbarEnabled = false
            }
            R.id.menu_bottom_add -> {
                if (currentFragment == addFragment) return false
                currentFragment = addFragment
                scrollToolbarEnabled = false
            }
            R.id.menu_bottom_profile -> {
                if (currentFragment == profileFragment) return false
                currentFragment = profileFragment
                scrollToolbarEnabled = true
            }
        }
        setScrollToolbarEnabled(scrollToolbarEnabled)
        currentFragment?.let {
            replaceFragment(R.id.main_fragment, it)
        }
        return true
    }

    override fun onPostCreated() {
        homeFragment.presenter.clear()

        if (supportFragmentManager.findFragmentByTag(profileFragment.javaClass.simpleName) != null)
        profileFragment.presenter.clear()

        binding.mainBottomBav.selectedItemId = R.id.menu_bottom_home
    }
}