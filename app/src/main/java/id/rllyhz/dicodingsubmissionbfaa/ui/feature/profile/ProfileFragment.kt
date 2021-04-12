package id.rllyhz.dicodingsubmissionbfaa.ui.feature.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.dicodingsubmissionbfaa.R
import id.rllyhz.dicodingsubmissionbfaa.databinding.FragmentProfileBinding
import id.rllyhz.dicodingsubmissionbfaa.ui.adapter.FollowingFollowersPagerAdapter
import id.rllyhz.dicodingsubmissionbfaa.util.DataConverter
import id.rllyhz.dicodingsubmissionbfaa.util.ResourceEvent
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!! // this approach is from official documentation

    private lateinit var mAdapter: FollowingFollowersPagerAdapter

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUI()
        collectState()

        // this should be from a real user retrieved from current user that being active
        // but for now this is just for dummy case, my own username as the user
        val username = resources.getString(R.string.user_detail_username)
        viewModel.getUserInfo(username)
    }

    private fun setUI() {

        binding.apply {

            swipeRefreshProfile.setColorSchemeColors(
                ContextCompat.getColor(requireContext(), R.color.redish_500),
                ContextCompat.getColor(requireContext(), R.color.blue_200),
                ContextCompat.getColor(requireContext(), R.color.blue_500)
            )

            mBtnProfileEditProfile.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.functionality_not_implemented_message),
                    Toast.LENGTH_LONG
                ).show()
            }

            viewModel.currentUserActive.observe(requireActivity()) { currentUserActive ->

                currentUserActive.apply {

                    Glide.with(requireActivity())
                        .load(avatarUrl)
                        .apply(RequestOptions.placeholderOf(R.drawable.bg_placeholder_images))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(sivProfileAvatar)

                    tvProfileFullname.text = fullName
                    tvProfileUsername.text = username
                    tvProfileBio.text = bio

                    tvProfileRepoCount.text =
                        DataConverter.getFollowingAndFollowersFormat(repositoriesCount)
                    tvProfileFollowersCount.text =
                        DataConverter.getFollowingAndFollowersFormat(followersCount)
                    tvProfileFollowingCount.text =
                        DataConverter.getFollowingAndFollowersFormat(followingCount)
                }

                mAdapter = FollowingFollowersPagerAdapter(
                    requireActivity() as AppCompatActivity,
                    currentUserActive
                )

                viewPagerProfile.adapter = mAdapter

                TabLayoutMediator(tabLayoutProfile, viewPagerProfile) { tab, position ->
                    tab.text =
                        resources.getString(FollowingFollowersPagerAdapter.TAB_TITLES[position])
                }.attach()

                // SwipeRefreshLayout
                swipeRefreshProfile.setOnRefreshListener {
                    viewModel.getUserInfo(currentUserActive.username)
                }
            }
        }
    }

    private fun collectState() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is ResourceEvent.Success<*> -> {
                        showSwipeRefreshLayout(false)
                        setupSuccessUI()
                    }
                    is ResourceEvent.Failure -> {
                        showSwipeRefreshLayout(false)
                        setupFailureUI()
                    }
                    is ResourceEvent.Loading -> {
                        showSwipeRefreshLayout(true)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupFailureUI() {
        binding.apply {
            sivProfileAvatar.setImageResource(R.drawable.bg_placeholder_images)
            tvProfileFullname.text = resources.getString(R.string.error_message_label)
            tvProfileUsername.text = DataConverter.STRING_NULL
            tvProfileBio.text = resources.getString(R.string.error_message_description)
            tvProfileFollowingCount.text = DataConverter.STRING_NULL
            tvProfileFollowersCount.text = DataConverter.STRING_NULL
            tvProfileRepoCount.text = DataConverter.STRING_NULL

            viewPagerProfile.visibility = View.GONE
            tabLayoutProfile.visibility = View.GONE
        }
    }

    private fun setupSuccessUI() {
        binding.apply {
            tabLayoutProfile.visibility = View.VISIBLE
            viewPagerProfile.visibility = View.VISIBLE
        }
    }

    private fun showSwipeRefreshLayout(state: Boolean) {
        binding.swipeRefreshProfile.isRefreshing = state
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null // avoiding memory leaks
    }
}