package id.rllyhz.dicodingsubmissionbfaa.ui.activity.userfav

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.dicodingsubmissionbfaa.R
import id.rllyhz.dicodingsubmissionbfaa.databinding.ActivityUserFavBinding
import id.rllyhz.dicodingsubmissionbfaa.ui.adapter.UserListAdapter

@AndroidEntryPoint
class UserFavActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserFavBinding
    private lateinit var userFavAdapter: UserListAdapter

    private val viewModel: UserFavModelView by viewModels()

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
        userFavAdapter = UserListAdapter()

        binding.recyclerviewUserFav.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@UserFavActivity)
            adapter = userFavAdapter
        }
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