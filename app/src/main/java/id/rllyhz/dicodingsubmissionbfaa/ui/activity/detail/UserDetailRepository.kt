package id.rllyhz.dicodingsubmissionbfaa.ui.activity.detail

import android.app.Application
import id.rllyhz.dicodingsubmissionbfaa.R
import id.rllyhz.dicodingsubmissionbfaa.api.GithubApi
import id.rllyhz.dicodingsubmissionbfaa.data.local.GithubDatabase
import id.rllyhz.dicodingsubmissionbfaa.data.local.userfav.UserFav
import id.rllyhz.dicodingsubmissionbfaa.data.model.User
import id.rllyhz.dicodingsubmissionbfaa.util.DispacherProvider
import id.rllyhz.dicodingsubmissionbfaa.util.Resource
import id.rllyhz.dicodingsubmissionbfaa.util.toUserModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserDetailRepository @Inject constructor(
    private val githubApi: GithubApi,
    private val application: Application,
    private val db: GithubDatabase,
    private val dispatchers: DispacherProvider
) {
    suspend fun getUserDetailOf(username: String): Resource<User> {
        return try {
            val response = githubApi.getUserDetailOf(username)
            val usersResponse = response.body()

            if (response.isSuccessful && usersResponse != null) {
                Resource.Success(usersResponse.toUserModel())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(application.getString(R.string.error_message))
        }
    }

    suspend fun doesUserExistInFav(username: String): Int {
        return db.userFavDao().doesUserExist(username)
    }

    fun addToFav(user: UserFav) {
        CoroutineScope(dispatchers.io).launch {
            db.userFavDao().add(user)
        }
    }

    fun removeFromFav(userId: Int) {
        CoroutineScope(dispatchers.io).launch {
            db.userFavDao().remove(userId)
        }
    }
}