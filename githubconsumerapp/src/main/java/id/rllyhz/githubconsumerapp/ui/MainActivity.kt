package id.rllyhz.githubconsumerapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.githubconsumerapp.R
import id.rllyhz.githubconsumerapp.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var userFavAdapter: UserFavAdapter? = null

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        swipeRefreshLoading(true)

        userFavAdapter = UserFavAdapter()

        binding.apply {
            Glide.with(this@MainActivity)
                .load(R.drawable.empty_illustration)
                .into(ivUserFavEmptyIllustration)

            rvGithubConsumerApp.apply {
                setHasFixedSize(true)
                adapter = userFavAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
            }

            viewModel.allUserFav.observe(this@MainActivity) { userFavs ->
                if (userFavs != null && userFavs.isNotEmpty()) {
                    userFavAdapter?.submitList(userFavs)
                    setStateUI(true)
                } else {
                    setStateUI(false)
                }

                swipeRefreshLoading(false)
            }

            swipeGithubConsumerApp.setOnRefreshListener {
                viewModel.getAllUserFavs()
            }
        }

        // load in the first time
        viewModel.getAllUserFavs()
    }

    private fun setStateUI(state: Boolean) {
        binding.apply {
            if (state) {
                rvGithubConsumerApp.visibility = View.VISIBLE
                rlGithubConsumerAppLayout.visibility = View.GONE
            } else {
                rvGithubConsumerApp.visibility = View.GONE
                rlGithubConsumerAppLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun swipeRefreshLoading(state: Boolean) {
        binding.swipeGithubConsumerApp.isRefreshing = state
    }

    override fun onDestroy() {
        super.onDestroy()
        userFavAdapter = null
    }
}