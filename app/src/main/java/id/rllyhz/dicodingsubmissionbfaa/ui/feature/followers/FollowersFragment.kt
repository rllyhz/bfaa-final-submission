package id.rllyhz.dicodingsubmissionbfaa.ui.feature.followers

import android.os.Bundle
import androidx.fragment.app.Fragment
import id.rllyhz.dicodingsubmissionbfaa.data.model.User

class FollowersFragment : Fragment() {

    companion object {
        private const val ARG_USER = "USER"

        fun newInstance(user: User): FollowersFragment {
            val args = Bundle().apply {
                putParcelable(ARG_USER, user)
            }

            return FollowersFragment().apply {
                arguments = args
            }
        }
    }
}