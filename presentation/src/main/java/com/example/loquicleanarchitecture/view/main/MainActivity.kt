package com.example.loquicleanarchitecture.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.core.view.MenuCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.domain.entities.Gender
import com.example.domain.entities.GenderPreference
import com.example.domain.entities.UserEntity
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import com.example.loquicleanarchitecture.view.login.AuthActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.*
import kotlinx.android.synthetic.main.menu_row_age_range.*
import kotlinx.android.synthetic.main.menu_row_gender.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.noHistory
import org.jetbrains.anko.toast
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity(), ViewModelStoreOwner {
    private val TAG: String? = this.javaClass.name

    private val appViewModelStore: ViewModelStore by lazy {
        ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return appViewModelStore
    }

    private lateinit var textViewAgerange: TextView
    private lateinit var textViewGenderValue: TextView

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    @Inject
    lateinit var auth: FirebaseAuth

    private lateinit var mainViewModel: MainViewModel

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mToolbar)

        drawerLayout = drawer_layout
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navigation_view.setupWithNavController(navController)
        setupSideNavigationMenu(navController)
        setupBottomNavMenu(navController)
        initNavigationDrawer()

//
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.chatlistFragment -> showBottomNav()
                R.id.profileFragment -> {
                    hideBottomNav(); }
                // else -> hideBottomNav()
            }
        }


//        mainViewModel = viewModel(viewModelFactory) {
//            observe(getUserDataLiveData(), ::updateUserUI)
//            failure(failure, ::handleFailure)
//        }

//        mainViewModel.loadUser()
    }

    private fun setupSideNavigationMenu(navController: NavController) {
        navigation_view?.let {
            setupWithNavController(mToolbar, navController, appBarConfiguration)
        }
    }

    private fun setupBottomNavMenu(navController: NavController) {
        bottom_nav?.let {
            setupWithNavController(it, navController)
        }
    }

    private fun initNavigationDrawer() {
        navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_drawer_gender -> navController.navigate(R.id.action_chatlistFragment_to_dialogDrawerSearchGender)
                R.id.item_drawer_age_range -> navController.navigate(R.id.dialogDrawerSearchAge)
                R.id.item_destination_profile -> {
                    navController.navigate(R.id.action_chatlistFragment_to_profileFragmentNav);drawerLayout.closeDrawer(
                        GravityCompat.START
                    )
                }

            }
            return@setNavigationItemSelectedListener false
        }
    }

    private fun showBottomNav() {
        bottom_nav.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        bottom_nav.visibility = View.GONE
        navigation_view.visibility = View.GONE

    }

    private fun updateUserUI(user: UserEntity) {
        Log.d(TAG, "UpdateUserUI")
        textView_header_nickname.text = user.nickname
        textView_header_age.text = user.age.toString()

        when (user.preferences_gender) {
            GenderPreference.FEMALE -> textView_menu_genderValue.text =
                getString(R.string.drawer_dialog_genderFemale)
            GenderPreference.MALE -> textView_menu_genderValue.text =
                getString(R.string.drawer_dialog_genderMale)
            GenderPreference.BOTH -> textView_menu_genderValue.text =
                getString(R.string.drawer_dialog_genderBoth)
        }

        when (user.gender) {
            Gender.FEMALE -> textView_header_gender.text =
                getString(R.string.drawer_dialog_genderFemale)
            Gender.MALE -> textView_header_gender.text =
                getString(R.string.drawer_dialog_genderMale)
        }
        textView_menu_ageRangeValue.text =
            getString(
                R.string.preferences_age_range,
                user.preferences_age_range_min,
                user.preferences_age_range_max
            )

        //   textView_profile_nickname_value?.text = user.nickname
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        MenuCompat.setGroupDividerEnabled(menu, false)
        return true
    }


    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        when {
            drawer_layout.isDrawerOpen(GravityCompat.START) -> drawer_layout.closeDrawer(
                GravityCompat.START
            )
            count == 0 -> moveTaskToBack(true)
            else -> super.onBackPressed()
        }
    }

    private fun initMenuReferences() {
        textViewAgerange = navigation_view.menu.findItem(R.id.item_drawer_age_range)
            .actionView.findViewById(R.id.textView_menu_ageRangeValue)
        textViewGenderValue = navigation_view.menu.findItem(R.id.item_drawer_gender)
            .actionView.findViewById(R.id.textView_menu_genderValue)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_logout -> logout()
        }
        return true
    }

    fun showId(v: View) {
        toast(v.id)
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(intentFor<AuthActivity>().noHistory())
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager.findFragmentByTag("ProfileFragment")
        fragment!!.onActivityResult(requestCode, resultCode, data)

    }
}