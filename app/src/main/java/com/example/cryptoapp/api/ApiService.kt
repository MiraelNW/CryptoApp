package com.example.cryptoapp.api

import com.example.cryptoapp.pojo.CoinInfoListOfData
import com.example.cryptoapp.pojo.CoinPriceInfoRawData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY)  apiKey:String ="be71ccd86f4210036c9406a695c9e1fa3a11baaa3b09e39f92486e63a59512fc",
        @Query(QUERY_PARAM_LIMIT) limit:Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tsym:String="USD"
    ): Single<CoinInfoListOfData>


    @GET("pricemultifull")
    fun getFulPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey:String = "be71ccd86f4210036c9406a695c9e1fa3a11baaa3b09e39f92486e63a59512fc",
        @Query(QUERY_PARAM_TO_SYMBOLS) tsyms:String = "USD",
        @Query(QUERY_PARAM_FROM_SYMBOLS) fsyms :String
    ):Single<CoinPriceInfoRawData>

    companion object {
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
        private const val QUERY_PARAM_API_KEY = "api_key"
    }
}