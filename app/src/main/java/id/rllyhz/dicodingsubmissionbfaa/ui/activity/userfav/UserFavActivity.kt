package id.rllyhz.dicodingsubmissionbfaa.ui.activity.userfav

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.dicodingsubmissionbfaa.R
import id.rllyhz.dicodingsubmissionbfaa.databinding.ActivityUserFavBinding

@AndroidEntryPoint
class UserFavActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserFavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        setupUI()
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = getString(R.string.action_bar_title_user_fav)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_left)
        }
    }

    private fun setupUI() {
        //TODO("Not yet implemented")
    }

    private fun showSwipeRefreshLayout(state: Boolean) {
        binding.swipeRefreshUserFav.isRefreshing = state
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}