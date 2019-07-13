package com.example.loquicleanarchitecture.view.main

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.fixtures.DialogsFixtures
import com.example.loquicleanarchitecture.view.main.chatlist.ChatlistActivity
import com.example.loquicleanarchitecture.view.profile.ProfileActivity
import com.stfalcon.chatkit.dialogs.DialogsListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : ChatlistActivity(), View.OnClickListener {


    private lateinit var mDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation_view.getHeaderView(0).setOnClickListener { startActivity<ProfileActivity>() }
        initToolbar()
        initAdapter()
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

    override fun onClick(v: View?) {
        v?.id?.let { toast(it) }
    }

}
