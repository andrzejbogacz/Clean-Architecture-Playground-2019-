package com.example.loquicleanarchitecture.di.auth

import javax.inject.Scope

import java.lang.annotation.RetentionPolicy.RUNTIME
import kotlin.annotation.Retention
import kotlin.annotation.MustBeDocumented

@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthScope
