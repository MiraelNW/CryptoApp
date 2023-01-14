package com.example.cryptoapp.DI

import android.app.Application
import com.example.cryptoapp.presentation.CoinPriceListActivity
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent

@Component(modules = [DataModule::class, WorkerModule::class])
interface ApplicationComponent {

    fun activityComponent():ActivityComponent.Factory

    @Component.Factory
    interface Factory{

        fun create(
            @BindsInstance application: Application
        ):ApplicationComponent
    }
}