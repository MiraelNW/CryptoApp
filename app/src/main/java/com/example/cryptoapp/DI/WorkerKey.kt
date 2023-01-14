package com.example.cryptoapp.DI

import androidx.lifecycle.ViewModel
import androidx.work.ListenableWorker
import dagger.MapKey
import kotlin.reflect.KClass


@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey (val value : KClass<out ListenableWorker>){
}