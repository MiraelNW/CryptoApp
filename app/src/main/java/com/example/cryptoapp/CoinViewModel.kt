package com.example.cryptoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cryptoapp.api.ApiFactory
import com.example.cryptoapp.dataBase.AppDataBase
import com.example.cryptoapp.pojo.CoinPriceInfo
import com.example.cryptoapp.pojo.CoinPriceInfoRawData
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    private val db = AppDataBase.getInstance(application)
    val priceList = db.coinPriceInfoDao().getPriceList()

    fun getDetailInfo(fsym:String):LiveData<CoinPriceInfo>{
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fsym)
    }

    init{
        loadData()
    }

    private fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo(limit = 50)
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") }
            .flatMap { ApiFactory.apiService.getFulPriceList(fsyms = it) }
            .map { getPriceListFromRawData(it) }
            .delaySubscription(10,TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
            }, {

            })
        compositeDisposable.add(disposable)
    }

    private fun getPriceListFromRawData(coinPriceInfoRawData: CoinPriceInfoRawData): List<CoinPriceInfo> {
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject
        jsonObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currJson = jsonObject.getAsJsonObject(coinKey)
            val currKeySet = currJson.keySet()
            for (currKey in currKeySet) {
                val priceInfo =
                    Gson().fromJson(currJson.getAsJsonObject(currKey), CoinPriceInfo::class.java)
                result.add(priceInfo)
            }

        }
        return result
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}