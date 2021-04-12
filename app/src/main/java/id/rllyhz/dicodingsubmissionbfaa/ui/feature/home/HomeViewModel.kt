package id.rllyhz.dicodingsubmissionbfaa.ui.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rllyhz.dicodingsubmissionbfaa.data.model.User
import id.rllyhz.dicodingsubmissionbfaa.ui.activity.main.MainRepository
import id.rllyhz.dicodingsubmissionbfaa.util.DispacherProvider
import id.rllyhz.dicodingsubmissionbfaa.util.ResourceEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispacherProvider
) : ViewModel() {

    private val _isTheFirstTime = MutableLiveData<Boolean>()
    val isTheFirstTime: LiveData<Boolean> = _isTheFirstTime

    private val _usersLiveData = MutableLiveData<List<User>>()
    val usersLiveData: LiveData<List<User>> = _usersLiveData

    private val _usersEvent = MutableStateFlow<ResourceEvent>(ResourceEvent.Empty)
    val usersStateFlow: StateFlow<ResourceEvent> = _usersEvent
}