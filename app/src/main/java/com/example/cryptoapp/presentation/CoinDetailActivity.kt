package com.example.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityCoinDetailBinding

class CoinDetailActivity : AppCompatActivity() {

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
        val fsym = intent.getStringExtra(EXTRA_FROM_FSYM) ?: ""
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container,CoinDetailFragment.newInstance(fsym))
                .commit()
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