package id.rllyhz.dicodingsubmissionbfaa.ui.activity.userfav

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rllyhz.dicodingsubmissionbfaa.data.local.userfav.UserFav
import javax.inject.Inject

@HiltViewModel
class UserFavModelView @Inject constructor(
    private val repository: UserFavRepository
) : ViewModel() {
    fun getAllUserFavs(): LiveData<List<UserFav>> =
        repository.getAllUserFavs().asLiveData()
}