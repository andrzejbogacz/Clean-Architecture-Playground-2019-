package com.example.loquicleanarchitecture.di

import com.example.data.FirebaseRepository
import com.example.domain.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth,
        firebaseStorage: StorageReference
    ): UserRepository {
        return FirebaseRepository(firebaseFirestore, firebaseAuth, firebaseStorage)
    }

    @Singleton
    @Provides
    internal fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    internal fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseStorage(firebaseAuth: FirebaseAuth): StorageReference {
        return FirebaseStorage.getInstance().reference.child(firebaseAuth.currentUser.toString())
    }


}