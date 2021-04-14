package id.rllyhz.githubconsumerapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.rllyhz.githubconsumerapp.data.local.DBGithubContract
import id.rllyhz.githubconsumerapp.data.model.UserFav
import id.rllyhz.githubconsumerapp.util.toUserFavs
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val app: Application
) : AndroidViewModel(app) {
    private val _allUserFavs = MutableLiveData<List<UserFav>>()
    val allUserFav: LiveData<List<UserFav>> = _allUserFavs

    fun getAllUserFavs() {
        val cursor = app.contentResolver.query(
            DBGithubContract.UserFavColumns.CONTENT_URI,
            null, null, null, null
        )

        _allUserFavs.value = cursor?.toUserFavs()
    }
}