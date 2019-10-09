package com.example.loquicleanarchitecture.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.core.view.MenuCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.domain.entities.GenderPreference
import com.example.domain.entities.UserEntity
import com.example.loquicleanarchitecture.BaseActivity
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.databinding.ActivityMainBinding
import com.example.loquicleanarchitecture.databinding.DrawerHeaderBinding
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import com.example.loquicleanarchitecture.helper.failure
import com.example.loquicleanarchitecture.helper.observe
import com.example.loquicleanarchitecture.helper.viewModel
import com.example.loquicleanarchitecture.view.login.AuthActivity
import com.example.loquicleanarchitecture.view.main.viewPager.MainPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_row_age_range.*
import kotlinx.android.synthetic.main.menu_row_gender.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.noHistory
import org.jetbrains.anko.toast
import javax.inject.Inject


class MainActivity : BaseActivity(), ViewModelStoreOwner {
    private val TAG: String? = this.javaClass.name

    lateinit var adapter: MainPagerAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    @Inject
    lateinit var auth: FirebaseAuth

    private lateinit var mainViewModel: SharedViewModel

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(mMainToolbar)

        adapter = MainPagerAdapter(
            supportFragmentManager
        )
        view_pager.adapter = adapter
        tabs.setupWithViewPager(view_pager)

        drawerLayout = drawer_layout

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        super.setNavvController(navController)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        navigation_view.setupWithNavController(navController)
        setupSideNavigationMenu(navController)
        initNavigationDrawer()

        initOnDestinationChangedListener()

        mainViewModel = viewModel(viewModelFactory) {
            observe(getUserDetailsLiveData(), ::updateMenuUI)
            observe(getNextUserLiveData(), ::startChat)
            failure(failure, ::handleFailure)
        }
        mainViewModel.loadUser()

        val viewHeader = navigation_view.getHeaderView(0)
        val navViewHeaderBinding = DrawerHeaderBinding.bind(viewHeader)

        navViewHeaderBinding.sharedViewModel = mainViewModel
        navViewHeaderBinding.lifecycleOwner = this

        binding.mChatToolbar.sharedViewModel = mainViewModel
        binding.lifecycleOwner = this
    }

    fun createUsers(v: View) {
        mainViewModel.queryUsers()
    }

    private fun initOnDestinationChangedListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.randomChatsFragment -> {
                    showViewPager()
                    super.showMainToolbar(navController, appBarConfiguration)
                }
                R.id.profileFragment -> hideViewPager()
                R.id.chatroomFragment -> {
                    hideViewPager()
                    super.showChatToolbar(navController, appBarConfiguration)
                }
            }
        }
    }

    private fun showViewPager() {
        view_pager.visibility = View.VISIBLE
        tabs.visibility = View.VISIBLE
    }

    private fun hideViewPager() {
        view_pager.visibility = View.GONE
        tabs.visibility = View.GONE
    }

    private fun setupSideNavigationMenu(navController: NavController) {
        navigation_view?.let {
            setupWithNavController(mMainToolbar, navController, appBarConfiguration)
        }
    }

    private fun initNavigationDrawer() {
        navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_drawer_gender -> navController.navigate(R.id.dialogDrawerSearchGender)
                R.id.item_drawer_age_range -> navController.navigate(R.id.dialogDrawerSearchAge)
                R.id.item_destination_profile -> {
                    navController.navigate(R.id.action_chatlistFragment_to_profileFragmentNav)
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            return@setNavigationItemSelectedListener false
        }
    }

    private fun updateMenuUI(user: UserEntity) {

        when (user.preferences_gender) {
            GenderPreference.FEMALE -> textView_menu_genderValue.text =
                getString(R.string.drawer_dialog_genderFemale)
            GenderPreference.MALE -> textView_menu_genderValue.text =
                getString(R.string.drawer_dialog_genderMale)
            GenderPreference.BOTH -> textView_menu_genderValue.text =
                getString(R.string.drawer_dialog_genderBoth)
        }

        textView_menu_ageRangeValue.text =
            getString(
                R.string.preferences_age_range,
                user.preferences_age_range_min,
                user.preferences_age_range_max
            )
    }

    fun showId(v: View) {
        toast(v.id)
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(intentFor<AuthActivity>().noHistory())
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        MenuCompat.setGroupDividerEnabled(menu, false)
        return true
    }

    override fun onBackPressed() {

        val isDrawerOpen = drawer_layout.isDrawerOpen(GravityCompat.START)
        val isMainScreen = navController.currentDestination?.id == R.id.randomChatsFragment

        when (isDrawerOpen) {
            true -> drawer_layout.closeDrawer(GravityCompat.START)
            false -> {
                if (isMainScreen) {
                    moveTaskToBack(true)
                } else super.onBackPressed()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_logout -> logout()
        }
        return false
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)

        navHost?.let { navFragment ->
            navFragment.childFragmentManager.primaryNavigationFragment?.onActivityResult(
                requestCode,
                resultCode,
                data
            )
        }
    }
}