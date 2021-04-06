package id.rllyhz.dicodingsubmissionbfaa.api

import id.rllyhz.dicodingsubmissionbfaa.data.model.User
import id.rllyhz.dicodingsubmissionbfaa.data.response.SearchUsersResponse
import id.rllyhz.dicodingsubmissionbfaa.data.response.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("users")
    suspend fun getUsers(): Response<List<UsersResponse>>

    @GET("users/{username}")
    suspend fun getUserDetailOf(
            @Path("username") username: String
    ): Response<User>

    @GET("users/{username}/following")
    suspend fun getFollowingOfUser(
            @Path("username") username: String
    ): Response<List<UsersResponse>>

    @GET("users/{username}/followers")
    suspend fun getFollowersOfUser(
            @Path("username") username: String
    ): Response<List<UsersResponse>>

    @GET("search/users")
    suspend fun searchUsers(
            @Query("q") query: String
    ): Response<SearchUsersResponse>


    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}