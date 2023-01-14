package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.data.database.CoinInfoDbModel
import com.example.cryptoapp.data.network.model.CoinInfoDto
import com.example.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptoapp.data.network.model.CoinNamesListDto
import com.example.cryptoapp.domain.CoinInfo
import com.google.gson.Gson
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CoinMapper @Inject constructor(){

    companion object{
        const val BASE_IMAGE_URL = "https://cryptocompare.com/"
    }


    fun mapDtoToDbModel(dto: CoinInfoDto) = CoinInfoDbModel(
        fromSymbol = dto.fromSymbol,
        toSymbol = dto.toSymbol,
        price = dto.price,
        lastUpdate = dto.lastUpdate,
        lastMarket = dto.lastmarket,
        highDay = dto.highday,
        lowDay = dto.lowday,
        imageUrl = BASE_IMAGE_URL + dto.imageurl
    )

    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.json
        jsonObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currJson = jsonObject.getAsJsonObject(coinKey)
            val currKeySet = currJson.keySet()
            for (currKey in currKeySet) {
                val priceInfo =
                    Gson().fromJson(currJson.getAsJsonObject(currKey), CoinInfoDto::class.java)
                result.add(priceInfo)
            }

        }
        return result
    }

    fun mapNamesListToString(namesListDto: CoinNamesListDto):String{
       return namesListDto.names?.map { it.coinNameDto?.name }?.joinToString(",") ?: ""
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel):CoinInfo{
        return CoinInfo(
            fromSymbol = dbModel.fromSymbol,
            toSymbol = dbModel.toSymbol,
            price = dbModel.price,
            lastUpdate = convertTimesTampToTime(dbModel.lastUpdate),
            lastMarket = dbModel.lastMarket,
            highDay = dbModel.highDay,
            lowDay = dbModel.lowDay,
            imageUrl = dbModel.imageUrl
        )
    }

    private fun convertTimesTampToTime(timestamp: Long?):String{
        if (timestamp==null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }
}
