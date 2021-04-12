package id.rllyhz.dicodingsubmissionbfaa.ui.feature.following

import android.os.Bundle
import androidx.fragment.app.Fragment
import id.rllyhz.dicodingsubmissionbfaa.data.model.User

class FollowingFragment : Fragment() {

    companion object {
        private const val ARG_USER = "USER"

        fun newInstance(user: User): FollowingFragment {
            val args = Bundle().apply {
                putParcelable(ARG_USER, user)
            }

            return FollowingFragment().apply {
                arguments = args
            }
        }
    }
}