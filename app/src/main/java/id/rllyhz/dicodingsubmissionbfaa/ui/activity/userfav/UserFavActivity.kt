package id.rllyhz.dicodingsubmissionbfaa.ui.activity.userfav

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.dicodingsubmissionbfaa.R
import id.rllyhz.dicodingsubmissionbfaa.databinding.ActivityUserFavBinding
import id.rllyhz.dicodingsubmissionbfaa.ui.adapter.UserListAdapter
import kotlinx.android.synthetic.main.activity_user_fav.view.*

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