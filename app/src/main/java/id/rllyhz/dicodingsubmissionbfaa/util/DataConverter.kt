package id.rllyhz.dicodingsubmissionbfaa.util

import android.icu.text.CompactDecimalFormat
import id.rllyhz.dicodingsubmissionbfaa.data.local.userfav.UserFav
import id.rllyhz.dicodingsubmissionbfaa.data.model.User
import id.rllyhz.dicodingsubmissionbfaa.data.response.SearchUsersResponse
import id.rllyhz.dicodingsubmissionbfaa.data.response.UserDetailResponse
import id.rllyhz.dicodingsubmissionbfaa.data.response.UsersResponse
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ln
import kotlin.math.pow

object DataConverter {
    const val STRING_NULL = "-"

    fun usersResponseToUsersModel(usersResponse: List<UsersResponse>): List<User> {
        val allUsers = mutableListOf<User>()

        for (user in usersResponse) {
            user.apply {
                allUsers.add(
                    User(
                        id = id,
                        username = username,
                        avatarUrl = avatarUrl
                    )
                )
            }
        }

        return allUsers
    }

    fun searchUsersToUserModels(searchUsersResponse: SearchUsersResponse): List<User> {
        val allUsers = mutableListOf<User>()

        if (searchUsersResponse.totalResults <= 0)
            return allUsers

        val usersResponse = searchUsersResponse.users

        for (user in usersResponse) {
            user.apply {
                allUsers.add(
                    User(
                        id = id,
                        username = username,
                        avatarUrl = avatarUrl
                    )
                )
            }
        }

        return allUsers
    }

    fun userDetailResponseToUserModel(userResponse: UserDetailResponse): User =
        User(
            userResponse.id,
            userResponse.username,
            userResponse.avatarUrl,
            userResponse.fullName ?: STRING_NULL,
            userResponse.companyName ?: STRING_NULL,
            userResponse.blogUrl ?: STRING_NULL,
            userResponse.location ?: STRING_NULL,
            userResponse.email ?: STRING_NULL,
            userResponse.twitterUsername ?: STRING_NULL,
            userResponse.bio ?: STRING_NULL,
            userResponse.repositoriesCount,
            userResponse.gistsCount,
            userResponse.followersCount,
            userResponse.followingCount,
        )

    fun userFavsToUserModels(userFavs: List<UserFav>): List<User> {
        val users = ArrayList<User>()

        for (user in userFavs) {
            users.add(
                User(
                    user.userId.toLong(),
                    user.username,
                    user.avatar_url
                )
            )
        }

        return users
    }

    fun getFollowingAndFollowersFormat(number: Long): String =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            CompactDecimalFormat.getInstance(Locale.US, CompactDecimalFormat.CompactStyle.SHORT)
                .format(number)
        } else {
            if (number < 1000)
                number.toString()
            else {
                val exp = (ln(number.toDouble()) / ln(1000.0)).toInt()
                String.format("%.1f %c", number / 1000.0.pow(exp.toDouble()), "kMBTPE"[exp - 1])
            }
        }
}