package id.rllyhz.dicodingsubmissionbfaa.api

import id.rllyhz.dicodingsubmissionbfaa.BuildConfig
import id.rllyhz.dicodingsubmissionbfaa.data.response.SearchUsersResponse
import id.rllyhz.dicodingsubmissionbfaa.data.response.UserDetailResponse
import id.rllyhz.dicodingsubmissionbfaa.data.response.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("users")
    @Headers("Authorization: token $GITHUB_API_TOKEN")
    suspend fun getUsers(): Response<List<UsersResponse>>

    @GET("users/{username}")
    @Headers("Authorization: token $GITHUB_API_TOKEN")
    suspend fun getUserDetailOf(
        @Path("username") username: String
    ): Response<UserDetailResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: token $GITHUB_API_TOKEN")
    suspend fun getFollowingOfUser(
        @Path("username") username: String
    ): Response<List<UsersResponse>>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $GITHUB_API_TOKEN")
    suspend fun getFollowersOfUser(
        @Path("username") username: String
    ): Response<List<UsersResponse>>

    @GET("search/users")
    @Headers("Authorization: token $GITHUB_API_TOKEN")
    suspend fun searchUsers(
        @Query("q") query: String
    ): Response<SearchUsersResponse>


    companion object {
        const val BASE_URL = "https://api.github.com/"
        private const val GITHUB_API_TOKEN = BuildConfig.GITHUB_API_TOKEN
    }
}