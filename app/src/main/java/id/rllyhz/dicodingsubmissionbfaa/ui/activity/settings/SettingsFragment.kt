package id.rllyhz.dicodingsubmissionbfaa.ui.activity.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import id.rllyhz.dicodingsubmissionbfaa.R


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }
}