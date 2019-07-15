package com.example.loquicleanarchitecture.view.main

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.fixtures.DialogsFixtures
import com.example.loquicleanarchitecture.view.dialogs.DialogDrawerSearchAgeRange
import com.example.loquicleanarchitecture.view.dialogs.DialogDrawerSearchGender
import com.example.loquicleanarchitecture.view.main.chatlist.ChatlistActivity
import com.example.loquicleanarchitecture.view.profile.ProfileActivity
import com.stfalcon.chatkit.dialogs.DialogsListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : ChatlistActivity() {


    private lateinit var mDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavigationDrawer()
        initToolbar()
        initAdapter()
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

    private fun displayGenderAlert(): Boolean {
       //val genderSearchDialog : GenderSearchDialog = Gender
       DialogDrawerSearchGender(this).show()
        return true
    }

    private fun displayaAgeRangeAlert(): Boolean {
       //val genderSearchDialog : GenderSearchDialog = Gender
       DialogDrawerSearchAgeRange(this).show()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_logout -> return  true //todo logout
        }
        return true
    }

    /*override fun onClick(v: View?) {
        v?.id?.let { toast(it) }
    }*/

}
