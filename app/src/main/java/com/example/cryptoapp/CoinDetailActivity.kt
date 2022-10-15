package com.example.cryptoapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coin_detail.*

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        if (!intent.hasExtra(EXTRA_FROM_FSYM)) {
            finish()
            return
        }
        val fsym = intent.getStringExtra(EXTRA_FROM_FSYM)

        viewModel = ViewModelProviders.of(this)[CoinViewModel::class.java]
        fsym?.let {
            viewModel.getDetailInfo(fsym).observe(this, Observer {
                tvPrice.text=it.price.toString()
                tvMinPrice.text=it.lowday.toString()
                tvMaxPrice.text=it.highday.toString()
                tvLastMarket.text=it.lastmarket
                tvLastUpdate.text=it.getFormatedTime()
                tvFromSymbol.text=it.fromSymbol
                tvToSymbol.text=it.toSymbol
                Picasso.get().load(it.getFulImageUrl()).into(ivLogoCoin)

            })
        }
    }

    companion object {
        private const val EXTRA_FROM_FSYM = "fsym"

        fun newIntent(context: Context, fsym: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_FSYM, fsym)
            return intent
        }
    }
}