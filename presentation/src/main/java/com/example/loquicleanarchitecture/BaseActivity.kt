package com.example.loquicleanarchitecture

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.FirebaseApp
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity : DaggerAppCompatActivity() {

    private lateinit var _navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)
    }

    fun showMainToolbar(navController: NavController, appBarConfiguration: AppBarConfiguration) {
        mMainToolbar.visibility = View.VISIBLE
        mChatToolbar.visibility = View.GONE
        setSupportActionBar(mMainToolbar)
        mMainToolbar.setupWithNavController(navController, appBarConfiguration)
        showViewPager()
    }

    protected fun showChatToolbar(
        navController: NavController,
        appBarConfiguration: AppBarConfiguration
    ) {
        mMainToolbar.visibility = View.GONE
        mChatToolbar.visibility = View.VISIBLE
        setSupportActionBar(mChatToolbar as Toolbar)
        (mChatToolbar as Toolbar).setupWithNavController(navController, appBarConfiguration)
    }

    @VisibleForTesting
    val progressDialog by lazy {
        ProgressDialog(this)
    }

    fun showProgressDialog() {
        progressDialog.setMessage(getString(R.string.loading))
        progressDialog.isIndeterminate = true
        progressDialog.show()
    }

    fun hideProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    public override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }

    protected fun showViewPager() {
        view_pager.visibility = View.VISIBLE
        tabs.visibility = View.VISIBLE
    }

    protected fun hideViewPager() {
        view_pager.visibility = View.GONE
        tabs.visibility = View.GONE
    }

    protected fun setBaseActivityNavController(navController: NavController) {
        _navController = navController
    }
}
