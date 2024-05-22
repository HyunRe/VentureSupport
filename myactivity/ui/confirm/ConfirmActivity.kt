//서버로부터 전송된 원장을 확인하고 선택함
package com.example.myactivity.ui.confirm

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.AppBarConfiguration.Builder.build
import androidx.navigation.ui.AppBarConfiguration.Builder.setOpenableLayout
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.myactivity.R
import com.example.myactivity.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class ConfirmActivity : AppCompatActivity() {
    private var mAppBarConfiguration: AppBarConfiguration? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        setSupportActionBar(binding.appBarConfirm.toolbar)
        if (binding.appBarConfirm.fab != null) {
            binding.appBarConfirm.fab.setOnClickListener { view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).setAnchorView(R.id.fab).show()
            }
        }
        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment?)!!
        val navController = navHostFragment.navController
        val navigationView: NavigationView = binding.navView
        if (navigationView != null) {
            mAppBarConfiguration = Builder(
                R.id.nav_transform, R.id.nav_reflow, R.id.nav_slideshow, R.id.nav_settings
            )
                .setOpenableLayout(binding.drawerLayout)
                .build()
            setupActionBarWithNavController(this, navController, mAppBarConfiguration!!)
            setupWithNavController(navigationView, navController)
        }
        val bottomNavigationView: BottomNavigationView =
            binding.appBarConfirm.contentConfirm.bottomNavView
        if (bottomNavigationView != null) {
            mAppBarConfiguration = Builder(
                R.id.nav_transform, R.id.nav_reflow, R.id.nav_slideshow
            )
                .build()
            setupActionBarWithNavController(this, navController, mAppBarConfiguration!!)
            setupWithNavController(bottomNavigationView, navController)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val result = super.onCreateOptionsMenu(menu)
        // Using findViewById because NavigationView exists in different layout files
        // between w600dp and w1240dp
        val navView = findViewById<NavigationView>(R.id.nav_view)
        if (navView == null) {
            // The navigation drawer already has the items including the items in the overflow menu
            // We only inflate the overflow menu if the navigation drawer isn't visible
            menuInflater.inflate(R.menu.overflow, menu)
        }
        return result
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_settings) {
            val navController = findNavController(this, R.id.nav_host_fragment_content_confirm)
            navController.navigate(R.id.nav_settings)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(this, R.id.nav_host_fragment_content_confirm)
        return (navigateUp(navController, mAppBarConfiguration!!)
                || super.onSupportNavigateUp())
    }
}