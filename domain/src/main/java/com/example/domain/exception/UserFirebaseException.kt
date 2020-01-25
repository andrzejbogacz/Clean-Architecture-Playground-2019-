package com.example.domain.exception


sealed class Exceptions : Throwable()

sealed class UserFirebaseException : Exceptions() {
    object UserNotExisting : UserFirebaseException()
    object UnknownException : UserFirebaseException()
    object UserNotFound : UserFirebaseException()

/**
 * Potential FirebaseFirestore exceptions to be handled :
 *
    CANCELLED
    UNKNOWN
    INVALID_ARGUMENT
    DEADLINE_EXCEEDED
    NOT_FOUND
    ALREADY_EXISTS
    PERMISSION_DENIED
    RESOURCE_EXHAUSTED
    FAILED_PRECONDITION
    ABORTED
    OUT_OF_RANGE
    UNIMPLEMENTED
    INTERNAL
    UNAVAILABLE
    DATA_LOSS
    UNAUTHENTICATED
    */
}