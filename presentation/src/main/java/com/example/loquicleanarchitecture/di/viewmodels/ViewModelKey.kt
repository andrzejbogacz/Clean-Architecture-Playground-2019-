package com.example.loquicleanarchitecture.di.viewmodels

import androidx.lifecycle.ViewModel
import dagger.MapKey

import java.lang.annotation.*
import kotlin.reflect.KClass


@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)


