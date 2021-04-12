package id.rllyhz.dicodingsubmissionbfaa.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.rllyhz.dicodingsubmissionbfaa.R
import id.rllyhz.dicodingsubmissionbfaa.data.model.User
import id.rllyhz.dicodingsubmissionbfaa.ui.feature.followers.FollowersFragment
import id.rllyhz.dicodingsubmissionbfaa.ui.feature.following.FollowingFragment

class FollowingFollowersPagerAdapter(activity: AppCompatActivity, private val user: User) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int =
        TAB_TITLES.size

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> FollowersFragment.newInstance(user)
            1 -> FollowingFragment.newInstance(user)
            else -> FollowersFragment.newInstance(user)
        }

    companion object {
        val TAB_TITLES = listOf(
            R.string.user_detail_followers_label,
            R.string.user_detail_following_label
        )
    }
}