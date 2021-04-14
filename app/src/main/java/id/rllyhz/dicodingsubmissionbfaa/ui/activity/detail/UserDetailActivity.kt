package id.rllyhz.dicodingsubmissionbfaa.ui.activity.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.dicodingsubmissionbfaa.R
import id.rllyhz.dicodingsubmissionbfaa.data.model.User
import id.rllyhz.dicodingsubmissionbfaa.databinding.ActivityUserDetailBinding
import id.rllyhz.dicodingsubmissionbfaa.ui.activity.userfav.UserFavActivity
import id.rllyhz.dicodingsubmissionbfaa.ui.adapter.FollowingFollowersPagerAdapter
import id.rllyhz.dicodingsubmissionbfaa.util.DataConverter
import id.rllyhz.dicodingsubmissionbfaa.util.ResourceEvent
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var mAdapter: FollowingFollowersPagerAdapter
    private var userExtra: User? = null

    private val viewModel: UserDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userExtra = intent.getParcelableExtra(USER_EXTRAS)

        if (userExtra != null) {
            userExtra?.username?.let { viewModel.getUser(it) }

            setupActionBar()
            setupUI()
        } else {
            finish()
            showFeedback(resources.getString(R.string.redirect_message), false)
        }
    }

    private fun setupUI() {
        binding.apply {

            viewModel.user.observe(this@UserDetailActivity) { user ->

                Glide.with(this@UserDetailActivity)
                    .load(user.avatarUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.bg_placeholder_images))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(sivUserDetailAvatar)

                tvUserDetailFullname.text = user.fullName
                tvUserDetailUsername.text = user.username
                tvUserDetailBio.text = user.bio
                tvUserDetailCompany.text = user.companyName
                tvUserDetailLocation.text = user.location

                // if blog url of user exists
                if (user.blogUrl != DataConverter.STRING_NULL) {
                    tvUserDetailBlog.text = SpannableString(user.blogUrl).apply {
                        setSpan(UnderlineSpan(), 0, user.blogUrl.length, 0)
                    }

                    tvUserDetailBlog.setOnClickListener {
                        val openLinkIntent = Intent(Intent.ACTION_VIEW, Uri.parse(user.blogUrl))
                        startActivity(openLinkIntent)
                    }
                } else {
                    tvUserDetailBlog.text = user.blogUrl
                }

                tvUserDetailFollowingFollowersCount.text = resources.getString(
                    R.string.user_detail_following_followers_format,
                    DataConverter.getFollowingAndFollowersFormat(user.followersCount),
                    DataConverter.getFollowingAndFollowersFormat(user.followingCount)
                )

                tvUserDetailRepositoryCount.text = resources.getQuantityString(
                    R.plurals.user_detail_repository_format,
                    user.repositoriesCount.toInt(),
                    user.repositoriesCount.toInt()
                )

                // also set pager adapter
                mAdapter = FollowingFollowersPagerAdapter(this@UserDetailActivity, user)
                viewPagerUserDetail.adapter = mAdapter

                TabLayoutMediator(tabLayoutUserDetail, viewPagerUserDetail) { tab, position ->
                    tab.text =
                        resources.getString(FollowingFollowersPagerAdapter.TAB_TITLES[position])
                }.attach()


                // toggleBtn user fav
                viewModel.apply {
                    isUserFavourite.observe(this@UserDetailActivity) { isFav ->
                        toggleBtnUserFav.isChecked = isFav
                    }

                    userExtra?.let { doesUserExistInFav(it.username) }
                }

                toggleBtnUserFav.setOnClickListener {
                    val isChecked = toggleBtnUserFav.isChecked

                    if (isChecked) {
                        userExtra?.let { viewModel.addToFav(it) }
                        showFeedback(
                            resources.getString(R.string.user_detail_added_to_fav_message),
                            true
                        )
                    } else {
                        userExtra?.let { viewModel.removeFromFav(it) }
                        showFeedback(
                            resources.getString(R.string.user_detail_removed_from_fav_message),
                            true
                        )
                    }
                }


                // swiperefresh
                swipeRefreshUserDetail.setOnRefreshListener {
                    viewModel.getUser(user.username)
                }
            }

            lifecycleScope.launchWhenStarted {
                viewModel.state.collect { event ->
                    when (event) {
                        is ResourceEvent.Success<*> -> {
                            showSwipeRefreshLayout(false)
                            setupSuccesUI()
                        }
                        is ResourceEvent.Failure -> {
                            showSwipeRefreshLayout(false)
                            setupFailureUI()
                        }
                        is ResourceEvent.Loading -> showSwipeRefreshLayout(true)
                        else -> showSwipeRefreshLayout(false)
                    }
                }
            }
        }
    }

    private fun setupSuccesUI() {
        binding.apply {
            viewPagerUserDetail.visibility = View.VISIBLE
            tabLayoutUserDetail.visibility = View.VISIBLE
            toggleBtnUserFav.visibility = View.VISIBLE
        }
    }

    private fun setupFailureUI() {
        binding.apply {
            sivUserDetailAvatar.setImageResource(R.drawable.bg_placeholder_images)
            tvUserDetailFullname.text = resources.getString(R.string.error_message_label)
            tvUserDetailUsername.text = DataConverter.STRING_NULL
            tvUserDetailBioLabel.text = resources.getString(R.string.error_message_label)
            tvUserDetailBio.text = resources.getString(R.string.error_message_description)
            tvUserDetailCompany.text = DataConverter.STRING_NULL
            tvUserDetailLocation.text = DataConverter.STRING_NULL
            tvUserDetailBlog.text = DataConverter.STRING_NULL
            tvUserDetailFollowingFollowersCount.text = DataConverter.STRING_NULL
            tvUserDetailRepositoryCount.text = DataConverter.STRING_NULL

            viewPagerUserDetail.visibility = View.GONE
            tabLayoutUserDetail.visibility = View.GONE
            toggleBtnUserFav.visibility = View.GONE
        }
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = getString(R.string.action_bar_title_user_detail)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_left)
        }
    }

    private fun showSwipeRefreshLayout(state: Boolean) {
        binding.swipeRefreshUserDetail.isRefreshing = state
    }

    private fun showFeedback(message: String, useSnackbar: Boolean) {
        if (useSnackbar) {
            Snackbar.make(binding.coordinatorLayoutUserDetail, message, Snackbar.LENGTH_LONG)
                .apply {
                    animationMode = Snackbar.ANIMATION_MODE_SLIDE
                    setAction(resources.getString(R.string.user_detail_snackbar_action_label)) {
                        startActivity(
                            Intent(this@UserDetailActivity, UserFavActivity::class.java)
                        )
                    }
                    show()
                }
        } else {
            Toast.makeText(
                applicationContext,
                message,
                Toast.LENGTH_LONG
            ).show()
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

    override fun onDestroy() {
        super.onDestroy()

        userExtra = null
    }

    companion object {
        const val USER_EXTRAS = "USER_EXTRAS"
    }
}