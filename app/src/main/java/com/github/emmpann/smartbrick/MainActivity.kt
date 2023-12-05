package com.github.emmpann.smartbrick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.github.emmpann.smartbrick.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController =
            (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).navController
        navController.navInflater.inflate(R.navigation.main_navigation).apply {
            setStartDestination(R.id.onBoardingFragment)
            navController.graph = this
        }
    }
}