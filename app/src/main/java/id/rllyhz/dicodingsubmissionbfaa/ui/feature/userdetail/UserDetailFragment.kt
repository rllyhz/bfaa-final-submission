package id.rllyhz.dicodingsubmissionbfaa.ui.feature.userdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.dicodingsubmissionbfaa.R
import id.rllyhz.dicodingsubmissionbfaa.data.model.User
import id.rllyhz.dicodingsubmissionbfaa.databinding.FragmentUserDetailBinding
import id.rllyhz.dicodingsubmissionbfaa.ui.adapter.FollowingFollowersPagerAdapter
import id.rllyhz.dicodingsubmissionbfaa.util.DataConverter
import id.rllyhz.dicodingsubmissionbfaa.util.ResourceEvent
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class UserDetailFragment : Fragment() {
    private lateinit var binding: FragmentUserDetailBinding
    private lateinit var mAdapter: FollowingFollowersPagerAdapter

    private val viewModel: UserDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val userExtra = intent.getParcelableExtra<User>(USER_EXTRAS)
        val userExtra = User(1, "", "")

        if (userExtra != null) {
            viewModel.getUser(userExtra.username)
            setupUI()
        } else {
            findNavController().navigateUp()
            showFeedback()
        }
    }

    private fun setupUI() {
        binding.apply {

            viewModel.user.observe(requireActivity()) { user ->

                Glide.with(requireActivity())
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
                mAdapter =
                    FollowingFollowersPagerAdapter(requireActivity() as AppCompatActivity, user)
                viewPagerUserDetail.adapter = mAdapter

                TabLayoutMediator(tabLayoutUserDetail, viewPagerUserDetail) { tab, position ->
                    tab.text =
                        resources.getString(FollowingFollowersPagerAdapter.TAB_TITLES[position])
                }.attach()


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
        }
    }

    private fun showSwipeRefreshLayout(state: Boolean) {
        binding.swipeRefreshUserDetail.isRefreshing = state
    }

    private fun showFeedback() {
        Toast.makeText(
            requireActivity().applicationContext,
            resources.getString(R.string.redirect_message),
            Toast.LENGTH_LONG
        ).show()
    }


    companion object {
        const val USER_EXTRAS = "USER_EXTRAS"
    }
}