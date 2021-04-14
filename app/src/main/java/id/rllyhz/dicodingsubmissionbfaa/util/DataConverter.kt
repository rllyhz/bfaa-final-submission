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

fun List<UsersResponse>.toUserModels(): List<User> {
    val allUsers = mutableListOf<User>()

    for (user in this) {
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

fun SearchUsersResponse.toUserModels(): List<User> {
    val allUsers = mutableListOf<User>()

    if (totalResults <= 0)
        return allUsers

    val usersResponse = users

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

fun UserDetailResponse.toUserModel(): User =
    User(
        id,
        username,
        avatarUrl,
        fullName ?: DataConverter.STRING_NULL,
        companyName ?: DataConverter.STRING_NULL,
        blogUrl ?: DataConverter.STRING_NULL,
        location ?: DataConverter.STRING_NULL,
        email ?: DataConverter.STRING_NULL,
        twitterUsername ?: DataConverter.STRING_NULL,
        bio ?: DataConverter.STRING_NULL,
        repositoriesCount,
        gistsCount,
        followersCount,
        followingCount
    )


@JvmName("toUserModelsUserFav")
fun List<UserFav>.toUserModels(): List<User> {
    val users = ArrayList<User>()

    for (user in this) {
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

object DataConverter {
    const val STRING_NULL = "-"

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