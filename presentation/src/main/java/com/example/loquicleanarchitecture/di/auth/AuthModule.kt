package com.example.loquicleanarchitecture.di.auth

import android.app.Application
import com.example.loquicleanarchitecture.R
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class AuthModule {
    @AuthScope
    @Provides
    internal fun provideFirestoreAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @AuthScope
    @Provides
    internal fun provideCallbackManager(): CallbackManager {
        return CallbackManager.Factory.create()
    }
    @AuthScope
    @Provides
    internal fun provideGoogleSignInClient(context : Application, gso : GoogleSignInOptions): GoogleSignInClient {
        return GoogleSignIn.getClient(context, gso)
    }

    @AuthScope
    @Provides
    internal fun provideGoogleSignInOptions(application : Application): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

}