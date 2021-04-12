package id.rllyhz.dicodingsubmissionbfaa.ui.activity.main

import android.app.Application
import id.rllyhz.dicodingsubmissionbfaa.R
import id.rllyhz.dicodingsubmissionbfaa.api.GithubApi
import id.rllyhz.dicodingsubmissionbfaa.data.model.User
import id.rllyhz.dicodingsubmissionbfaa.util.DataConverter
import id.rllyhz.dicodingsubmissionbfaa.util.Resource
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
                val allUsers = DataConverter.usersResponseToUsersModel(usersResponse)
                Resource.Success(allUsers)
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
                val allUsers = DataConverter.userDetailResponseToUserModel(usersResponse)
                Resource.Success(allUsers)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(application.getString(R.string.error_message))
        }
    }
}