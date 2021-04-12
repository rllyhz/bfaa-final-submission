package id.rllyhz.dicodingsubmissionbfaa.ui.activity.userfav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.rllyhz.dicodingsubmissionbfaa.R
import id.rllyhz.dicodingsubmissionbfaa.databinding.ActivityUserFavBinding

class UserFavActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserFavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = getString(R.string.action_bar_title_user_fav)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_left)
        }
    }
}