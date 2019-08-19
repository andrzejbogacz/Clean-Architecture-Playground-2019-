package com.example.loquicleanarchitecture.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.example.domain.entities.UserEntity
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import com.example.loquicleanarchitecture.fixtures.DialogsFixtures
import com.example.loquicleanarchitecture.helper.failure
import com.example.loquicleanarchitecture.helper.observe
import com.example.loquicleanarchitecture.helper.viewModel
import com.example.loquicleanarchitecture.view.dialogs.DialogDrawerSearchAge
import com.example.loquicleanarchitecture.view.dialogs.DialogDrawerSearchGender
import com.example.loquicleanarchitecture.view.login.AuthActivity
import com.example.loquicleanarchitecture.view.main.chatlist.ChatlistActivity
import com.example.loquicleanarchitecture.view.profile.ProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.stfalcon.chatkit.dialogs.DialogsListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.*
import kotlinx.android.synthetic.main.menu_row_gender.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import javax.inject.Inject


class MainActivity : ChatlistActivity(), ViewModelStoreOwner {
    private val TAG: String? = this.javaClass.name

    private val appViewModelStore: ViewModelStore by lazy {
        ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return appViewModelStore
    }

    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    lateinit var textView_ageRange: TextView
    lateinit var textView_genderValue: TextView

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    @Inject
    lateinit var auth: FirebaseAuth

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavigationDrawer()
        initToolbar()
        initAdapter()
        initMenuReferences()

        mainViewModel = viewModel(viewModelFactory) {
            observe(userData, ::updateUserUI)
            failure(failure, ::handleFailure)
        }

        mainViewModel.loadUser()
    }

    fun updateUserUI(user: UserEntity) {
        textView_header_nickname.text = user.nickname
        textView_header_gender.text = user.gender.toString()
        textView_header_age.text = user.age.toString()

        textView_menu_genderValue.text = user.preferences_gender.toString()
    }

    private fun displayGenderAlert() {
        DialogDrawerSearchGender().show(supportFragmentManager, "gender")
    }

    private fun displayaAgeRangeAlert() {

        DialogDrawerSearchAge().show(supportFragmentManager, "ageRange")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun initToolbar() {
        // Setup Actionbar / Toolbar
        setSupportActionBar(mToolbar as Toolbar?)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        // Setup Navigation Drawer Layout
        mDrawerToggle =
            object : ActionBarDrawerToggle(
                this,
                drawer_layout,
                mToolbar as Toolbar,
                R.string.drawer_open,
                R.string.drawer_close
            ) {

            }
        drawer_layout.addDrawerListener(mDrawerToggle)

        drawer_layout.post { mDrawerToggle.syncState() }
    }

    private fun initAdapter() {
        super.dialogsAdapter = DialogsListAdapter(super.imageLoader)
        super.dialogsAdapter.setItems(DialogsFixtures.dialogs)

        super.dialogsAdapter.setOnDialogClickListener(this)
        super.dialogsAdapter.setOnDialogLongClickListener(this)

        dialogsList.setAdapter(super.dialogsAdapter)
    }

    private fun initNavigationDrawer() {
        navigation_view.getHeaderView(0).setOnClickListener { startActivity<ProfileActivity>() }
        navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_drawer_gender -> displayGenderAlert()
                R.id.item_drawer_age_range -> displayaAgeRangeAlert()
            }
            return@setNavigationItemSelectedListener false
        }
    }

    private fun initMenuReferences() {
        textView_ageRange = navigation_view.menu.findItem(R.id.item_drawer_age_range)
            .actionView.findViewById(R.id.textView_menu_ageRangeValue)
        textView_genderValue = navigation_view.menu.findItem(R.id.item_drawer_gender)
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

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(intentFor<AuthActivity>().newTask())
    }

}
