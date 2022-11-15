package com.example.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    private val binding by lazy {
        ActivityCoinDetailBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (!intent.hasExtra(EXTRA_FROM_FSYM)) {
            finish()
            return
        }
        val fsym = intent.getStringExtra(EXTRA_FROM_FSYM)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        fsym?.let {
            viewModel.getDetailInfo(fsym).observe(this, Observer {
                with(binding)
                {
                    tvPrice.text = it.price.toString()
                    tvMinPrice.text = it.lowDay.toString()
                    tvMaxPrice.text = it.highDay.toString()
                    tvLastMarket.text = it.lastMarket
                    tvLastUpdate.text = it.lastUpdate
                    tvFromSymbol.text = it.fromSymbol
                    tvToSymbol.text = it.toSymbol
                    Picasso.get().load(it.imageUrl).into(ivLogoCoin)
                }

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