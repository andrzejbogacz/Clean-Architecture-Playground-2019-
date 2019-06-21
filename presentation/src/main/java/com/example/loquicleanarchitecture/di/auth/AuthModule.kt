package com.example.loquicleanarchitecture.di.auth

import com.google.firebase.firestore.auth.User
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class AuthModule {
    @AuthScope
    @Provides
    internal fun someUser(): User {
        return User("aa")
    }
}