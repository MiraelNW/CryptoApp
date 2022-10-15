package com.example.cryptoapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_coin_info.view.*

class CoinInfoAdapter : RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: List<CoinPriceInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onCoinClickListener : OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_coin_info, parent, false
        )
        return CoinInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder) {
            with(coin) {
                tvSymbols.text = fromSymbol + "/" + toSymbol
                tvPrice.text = price.toString()
                tvLastUpdate.text = getFormatedTime()
                Picasso.get().load(getFulImageUrl()).into(ivLogoCoin)
                itemView.setOnClickListener{
                    onCoinClickListener?.OnCoinClick(this)
                }
            }
        }


    }

    override fun getItemCount() = coinInfoList.size


    inner class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivLogoCoin = itemView.ivLogoCoin
        val tvLastUpdate = itemView.tvLastUpdate
        val tvPrice = itemView.tvPrice
        val tvSymbols = itemView.tvSymbols
    }

    interface OnCoinClickListener{
        fun OnCoinClick(coinPriceInfo: CoinPriceInfo)
    }
}