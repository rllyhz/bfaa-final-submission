package id.rllyhz.dicodingsubmissionbfaa.ui.activity.detail

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
class UserDetailViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispacherProvider
) : ViewModel() {

    private val _currentUser = MutableLiveData<User>()
    val user: LiveData<User> = _currentUser

    private val _state = MutableStateFlow<ResourceEvent>(ResourceEvent.Empty)
    val state: StateFlow<ResourceEvent> = _state

    fun getUser(username: String) {
        viewModelScope.launch(dispatchers.io) {
            _state.value = ResourceEvent.Loading

            when (val userDetailResponse = repository.getUserDetailOf(username)) {
                is Resource.Error -> _state.value =
                    ResourceEvent.Failure(userDetailResponse.message!!)
                is Resource.Success -> {
                    _state.value = ResourceEvent.Success(null, userDetailResponse.data!!)

                    withContext(dispatchers.main) {
                        _currentUser.value = userDetailResponse.data
                    }
                }
            }
        }
    }
}