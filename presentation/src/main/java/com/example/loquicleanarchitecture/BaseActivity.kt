package com.example.loquicleanarchitecture

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.domain.entities.UserEntity
import com.example.domain.entities.UserPhotos
import com.google.firebase.FirebaseApp
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity : DaggerAppCompatActivity() {


    private val userDetails = ObservableField<UserEntity>()
    private val userPhotos = ObservableField<UserPhotos>()

    private lateinit var _navController: NavController
    fun setNavvController(navController: NavController) {
        _navController = navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)
    }

    fun startChat(pair: Pair<*, *>) {
        //todo pass user as args
        val bundle: Bundle = Bundle().apply { putSerializable("userAndPhotos", pair) }

        //todo 1.prepareChatToolbar(photos, userDetails)

        _navController.navigate(R.id.action_chatlistFragment_to_chatroomFragment, bundle)
    }

    fun showMainToolbar(navController: NavController, appBarConfiguration: AppBarConfiguration) {
        mMainToolbar.visibility = View.VISIBLE
        mChatToolbar.visibility = View.GONE
        setSupportActionBar(mMainToolbar)
        mMainToolbar.setupWithNavController(navController, appBarConfiguration)
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

}
