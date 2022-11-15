package com.example.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityMainBinding
import com.example.cryptoapp.domain.CoinInfo
import com.example.cryptoapp.presentation.adapter.CoinInfoAdapter

class CoinPriceListActivity : AppCompatActivity() {
    private lateinit var viewModel: CoinViewModel

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun OnCoinClick(coinInfo: CoinInfo) {
                if (isOnePaneMode()){
                    launchDetailActivity(coinInfo.fromSymbol)
                }else{
                    launchDetailFragment(coinInfo.fromSymbol)
                }
            }
        }
        binding.rvCoinPriceList.adapter = adapter
        binding.rvCoinPriceList.itemAnimator=null
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.coinInfoList.observe(this, Observer {
            adapter.submitList(it)
        })

    }

    private fun isOnePaneMode() = binding.fragmentContainer == null

    private fun launchDetailActivity(fromSymbol: String){
        val intent = CoinDetailActivity.newIntent(
            this@CoinPriceListActivity,
            fromSymbol
        )
        startActivity(intent)
    }

    private fun launchDetailFragment(fromSymbol : String){
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,CoinDetailFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }

}

