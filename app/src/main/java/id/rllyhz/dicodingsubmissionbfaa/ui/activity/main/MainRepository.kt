package id.rllyhz.dicodingsubmissionbfaa.ui.activity.main

import android.app.Application
import id.rllyhz.dicodingsubmissionbfaa.api.GithubApi
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val githubApi: GithubApi,
    private val application: Application
)