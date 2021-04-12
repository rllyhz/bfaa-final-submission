package id.rllyhz.dicodingsubmissionbfaa.ui.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.dicodingsubmissionbfaa.R
import id.rllyhz.dicodingsubmissionbfaa.data.model.User
import id.rllyhz.dicodingsubmissionbfaa.databinding.FragmentHomeBinding
import id.rllyhz.dicodingsubmissionbfaa.ui.activity.detail.UserDetailActivity
import id.rllyhz.dicodingsubmissionbfaa.ui.adapter.UserListAdapter
import id.rllyhz.dicodingsubmissionbfaa.util.ResourceEvent
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : Fragment(), UserListAdapter.ItemClickCallback {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!! // this approach is from official documentation

    private val viewModel: HomeViewModel by viewModels()

    private var usersAdapter: UserListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        usersAdapter = UserListAdapter()
        usersAdapter?.setOnItemListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setInitialUI()
        collectUsersStateFlow()

        viewModel.usersLiveData.observe(requireActivity()) { users ->
            usersAdapter?.submitList(users)
        }

        viewModel.loadUsers()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null // avoiding memory leaks
        usersAdapter = null
    }

    private fun setInitialUI() {
        binding.apply {
            swipeRefreshHome.setColorSchemeColors(
                ContextCompat.getColor(requireContext(), R.color.redish_500),
                ContextCompat.getColor(requireContext(), R.color.blue_200),
                ContextCompat.getColor(requireContext(), R.color.blue_500)
            )

            showSwipeRefreshLayout(false)

            recyclerviewUsers.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = usersAdapter
            }

            // swiperefresh
            swipeRefreshHome.setOnRefreshListener {
                viewModel.loadUsers()
            }
        }
    }

    private fun collectUsersStateFlow() {
        binding.apply {
            lifecycleScope.launchWhenStarted {
                viewModel.usersStateFlow.collect { event ->
                    when (event) {
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
                            setupLoadingUI()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun setupLoadingUI() {
        binding.apply {
            tvHomeUserListLabel.visibility = View.GONE
            recyclerviewUsers.visibility = View.GONE
        }
    }

    private fun setupFailureUI() {
        binding.apply {
            mcardHomeHeadlineContainer.visibility = View.GONE
            tvHomeUserListLabel.visibility = View.GONE
            recyclerviewUsers.visibility = View.GONE
            tvHomeErrorMessage.visibility = View.VISIBLE
            tvHomeErrorMessageDescription.visibility = View.VISIBLE
        }
    }

    private fun setupSuccessUI() {
        binding.apply {
            mcardHomeHeadlineContainer.visibility = View.VISIBLE
            tvHomeUserListLabel.visibility = View.VISIBLE
            recyclerviewUsers.visibility = View.VISIBLE
            tvHomeErrorMessage.visibility = View.GONE
            tvHomeErrorMessageDescription.visibility = View.GONE
        }
    }

    private fun showSwipeRefreshLayout(state: Boolean) {
        binding.swipeRefreshHome.isRefreshing = state
    }

    override fun onDetailIconClick(user: User) {
        val userDetailIntent = Intent(requireActivity(), UserDetailActivity::class.java).apply {
            putExtra(UserDetailActivity.USER_EXTRAS, user)
        }

        requireActivity().startActivity(userDetailIntent)
    }
}