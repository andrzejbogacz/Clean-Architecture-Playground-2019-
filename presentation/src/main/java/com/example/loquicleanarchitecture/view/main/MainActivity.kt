package com.example.loquicleanarchitecture.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.fixtures.DialogsFixtures
import com.example.loquicleanarchitecture.view.dialogs.DialogDrawerSearchAge
import com.example.loquicleanarchitecture.view.dialogs.DialogDrawerSearchGender
import com.example.loquicleanarchitecture.view.login.AuthActivity
import com.example.loquicleanarchitecture.view.main.chatlist.ChatlistActivity
import com.example.loquicleanarchitecture.view.profile.ProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.stfalcon.chatkit.dialogs.DialogsListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class MainActivity : ChatlistActivity(),
    DialogDrawerSearchAge.AgeRangeListener,
    DialogDrawerSearchGender.GenderListener {

    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    lateinit var textView_ageRange: TextView
    lateinit var textView_genderValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavigationDrawer()
        initToolbar()
        initAdapter()
        initMenuReferences()

    }

    override fun applyAgeRange(ageRange: String) {
        textView_ageRange.text = ageRange
    }

    override fun applyGender(gender: Int) {
        textView_genderValue.setText(gender)
    }

    private fun initMenuReferences() {
        textView_ageRange = navigation_view.menu.findItem(R.id.item_drawer_age_range)
            .actionView.findViewById(R.id.textView_menu_ageRangeValue)
        textView_genderValue = navigation_view.menu.findItem(R.id.item_drawer_gender)
            .actionView.findViewById(R.id.textView_menu_genderValue)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
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

    private fun displayGenderAlert() {
        DialogDrawerSearchGender().show(supportFragmentManager, "gender")
    }

    private fun displayaAgeRangeAlert() {

        DialogDrawerSearchAge().show(supportFragmentManager, "ageRange")
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

                override fun onDrawerOpened(drawerView: View) {
                    super.onDrawerOpened(drawerView)
                }

                override fun onDrawerClosed(drawerView: View) {
                    super.onDrawerClosed(drawerView)
                }
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
