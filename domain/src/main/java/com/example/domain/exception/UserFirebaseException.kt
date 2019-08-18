package com.example.domain.exception


sealed class Exceptions : Throwable()

sealed class UserFirebaseException : Exceptions() {
    object UserNotExisting : UserFirebaseException()
    object UnknownException : UserFirebaseException()

}