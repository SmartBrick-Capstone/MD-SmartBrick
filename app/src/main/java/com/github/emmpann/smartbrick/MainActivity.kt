package com.github.emmpann.smartbrick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.core.splashscreen.SplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.github.emmpann.smartbrick.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    private lateinit var splashScreen: SplashScreen
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        splashScreen =
//        splashScreen.setKeepOnScreenCondition{ true }

        navController = (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.navView.visibility = View.VISIBLE
                }
                R.id.profileFragment -> {
                    binding.navView.visibility = View.VISIBLE
                }
                else -> {
                    binding.navView.visibility = View.GONE
                }
            }
        }

        setStartDestination()
    }

    private fun setStartDestination() {
        viewModel.token.observe(this) {
            navController.navInflater.inflate(R.navigation.main_navigation).apply {
                setStartDestination(if (it.isNotEmpty()) R.id.homeFragment else R.id.loginFragment)
                navController.graph = this
//                splashScreen.setKeepOnScreenCondition { false }
                setupWithNavController(binding.navView, navController)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return true
    }
}