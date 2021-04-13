package id.rllyhz.dicodingsubmissionbfaa.ui.activity.userfav

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.dicodingsubmissionbfaa.R
import id.rllyhz.dicodingsubmissionbfaa.data.model.User
import id.rllyhz.dicodingsubmissionbfaa.databinding.ActivityUserFavBinding
import id.rllyhz.dicodingsubmissionbfaa.ui.activity.detail.UserDetailActivity
import id.rllyhz.dicodingsubmissionbfaa.ui.adapter.UserListAdapter
import id.rllyhz.dicodingsubmissionbfaa.util.DataConverter
import kotlinx.android.synthetic.main.activity_user_fav.view.*

@AndroidEntryPoint
class UserFavActivity : AppCompatActivity(), UserListAdapter.ItemClickCallback {
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
        userFavAdapter.setOnItemListener(this)

        binding.apply {
            Glide.with(this@UserFavActivity)
                .load(
                    ContextCompat.getDrawable(
                        this@UserFavActivity,
                        R.drawable.empty_illustration
                    )
                )
                .into(ivUserFavEmptyIllustration)

            recyclerviewUserFav.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@UserFavActivity)
                adapter = userFavAdapter
            }
        }

        viewModel.getAllUserFavs().observe(this) { userFavs ->
            if (userFavs != null && userFavs.isNotEmpty()) {
                val users = DataConverter.userFavsToUserModels(userFavs)
                userFavAdapter.submitList(users)
                setUIState(true)
            } else {
                setUIState(false)
            }
        }
    }

    private fun setUIState(state: Boolean) {
        binding.apply {
            if (state) {
                recyclerviewUserFav.visibility = View.VISIBLE
                rlUserFavEmptyLayout.visibility = View.GONE
            } else {
                recyclerviewUserFav.visibility = View.GONE
                rlUserFavEmptyLayout.visibility = View.VISIBLE
            }
        }
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

    override fun onDetailIconClick(user: User) {
        startActivity(
            Intent(this, UserDetailActivity::class.java).apply {
                putExtra(UserDetailActivity.USER_EXTRAS, user)
            }
        )
    }
}