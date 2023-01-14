package com.example.cryptoapp.DI

import com.example.cryptoapp.presentation.CoinApp
import com.example.cryptoapp.presentation.CoinDetailFragment
import com.example.cryptoapp.presentation.CoinPriceListActivity
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
interface ActivityComponent {

    fun inject(activity: CoinPriceListActivity)
    fun inject (fragment: CoinDetailFragment)
    fun inject (app: CoinApp)

    @Subcomponent.Factory
    interface Factory{
        fun create():ActivityComponent
    }

}