package id.rllyhz.dicodingsubmissionbfaa.ui.feature.explore

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
class ExploreViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispacherProvider
) : ViewModel() {

    private var _state: MutableStateFlow<ResourceEvent> = MutableStateFlow(ResourceEvent.Empty)
    val state: StateFlow<ResourceEvent> = _state

    private var _searchResults: MutableLiveData<List<User>> = MutableLiveData()
    val searchResults: LiveData<List<User>> = _searchResults

    private var _lastQuery: MutableLiveData<String> = MutableLiveData()
    val lastQuery: LiveData<String> = _lastQuery

    fun setLastQuery(newQuery: String) {
        _lastQuery.value = newQuery
    }

    fun searchUsers(query: String) {
        viewModelScope.launch(dispatchers.io) {
            _state.value = ResourceEvent.Loading

            when (val response = repository.searchUsers(query)) {
                is Resource.Error -> _state.value = ResourceEvent.Failure(response.message!!)
                is Resource.Success -> {
                    _state.value = ResourceEvent.Success(null, null)

                    withContext(dispatchers.main) {
                        _searchResults.value = response.data!!
                    }
                }
            }
        }
    }
}