package com.example.cryptoapp.data.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.cryptoapp.data.database.CoinInfoDao
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiService
import javax.inject.Inject
import javax.inject.Provider

class RefreshDataWorkerFactory @Inject constructor(
    private val workerProviders : @JvmSuppressWildcards Map<Class<out ListenableWorker>,Provider<ChildWorkerFactory>>

) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when(workerClassName){
            RefreshDataWorker::class.qualifiedName -> {
               val childWorkerFactory = workerProviders[RefreshDataWorker::class.java]?.get()
                return childWorkerFactory?.create(appContext,workerParameters)
            }
            else -> null
        }
    }

}