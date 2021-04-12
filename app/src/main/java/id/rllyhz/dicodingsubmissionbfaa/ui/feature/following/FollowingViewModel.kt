package id.rllyhz.dicodingsubmissionbfaa.ui.feature.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rllyhz.dicodingsubmissionbfaa.data.model.User
import id.rllyhz.dicodingsubmissionbfaa.ui.activity.main.MainRepository
import id.rllyhz.dicodingsubmissionbfaa.util.DispacherProvider
import id.rllyhz.dicodingsubmissionbfaa.util.Resource
import id.rllyhz.dicodingsubmissionbfaa.util.ResourceEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispacherProvider
) : ViewModel() {

    private val _state: MutableStateFlow<ResourceEvent> = MutableStateFlow(ResourceEvent.Empty)
    val state: StateFlow<ResourceEvent> = _state

    private val _following: MutableLiveData<List<User>> = MutableLiveData()
    val followings: LiveData<List<User>> = _following

    fun getFollowingOfUser(username: String) {
        viewModelScope.launch(dispatchers.io) {
            _state.value = ResourceEvent.Loading

            when (val usersResponse = repository.getFollowingOfUser(username)) {
                is Resource.Error -> _state.value =
                    ResourceEvent.Failure(usersResponse.message!!)
                is Resource.Success -> {
                    _state.value = ResourceEvent.Success(null, null)

                    withContext(dispatchers.main) {
                        _following.value = usersResponse.data!!
                    }
                }
            }
        }
    }
}