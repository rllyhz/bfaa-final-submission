package id.rllyhz.dicodingsubmissionbfaa.ui.activity.main

import android.app.Application
import id.rllyhz.dicodingsubmissionbfaa.R
import id.rllyhz.dicodingsubmissionbfaa.api.GithubApi
import id.rllyhz.dicodingsubmissionbfaa.data.model.User
import id.rllyhz.dicodingsubmissionbfaa.util.Resource
import id.rllyhz.dicodingsubmissionbfaa.util.toUserModel
import id.rllyhz.dicodingsubmissionbfaa.util.toUserModels
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val githubApi: GithubApi,
    private val application: Application
) {
    suspend fun getUsers(): Resource<List<User>> {
        return try {
            val response = githubApi.getUsers()
            val usersResponse = response.body()

            if (response.isSuccessful && usersResponse != null) {
                Resource.Success(usersResponse.toUserModels())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(application.getString(R.string.error_message))
        }
    }

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

    suspend fun getFollowersOfUser(username: String): Resource<List<User>> {
        return try {
            val response = githubApi.getFollowersOfUser(username)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                Resource.Success(result.toUserModels())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(application.getString(R.string.error_message))
        }
    }

    suspend fun getFollowingOfUser(username: String): Resource<List<User>> {
        return try {
            val response = githubApi.getFollowingOfUser(username)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                Resource.Success(result.toUserModels())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(application.getString(R.string.error_message))
        }
    }

    suspend fun searchUsers(query: String): Resource<List<User>> {
        return try {
            val response = githubApi.searchUsers(query)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                Resource.Success(result.toUserModels())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(application.getString(R.string.error_message))
        }
    }
}