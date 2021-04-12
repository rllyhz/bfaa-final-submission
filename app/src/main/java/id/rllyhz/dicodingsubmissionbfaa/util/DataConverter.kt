package id.rllyhz.dicodingsubmissionbfaa.util

import android.icu.text.CompactDecimalFormat
import id.rllyhz.dicodingsubmissionbfaa.data.model.User
import id.rllyhz.dicodingsubmissionbfaa.data.response.SearchUsersResponse
import id.rllyhz.dicodingsubmissionbfaa.data.response.UsersResponse
import java.util.*
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